package ui.reaction;

import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import domain.waifu.Waifu;
import logic.messages.MessageSender;
import logic.waifu.JikanFetcher;
import domain.PlayerLoader;
import domain.WaifuLoader;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.listener.message.reaction.ReactionAddListener;
import ui.embeds.waifu.WaifuDeletedEmbed;
import ui.embeds.waifu.WaifuEmbed;
import ui.messages.messages.*;
import util.Emojis;
import util.Terminal;

import java.util.Optional;

public class WaifuEditListener extends MyAbstractReactionListener implements ReactionAddListener {

    private final Waifu waifu;
    private final Player player;
    private final PlayerLoader playerLoader;
    private final WaifuLoader waifuLoader;
    private final JikanFetcher jikanFetcher;
    private final MessageSender messageSender;

    public WaifuEditListener(Waifu waifu, Player owner, PlayerLoader playerLoader,
                             WaifuLoader waifuLoader, JikanFetcher jikanFetcher, MessageSender messageSender) {
        this.waifu = waifu;
        this.player = owner;
        this.playerLoader = playerLoader;
        this.waifuLoader = waifuLoader;
        this.jikanFetcher = jikanFetcher;
        this.messageSender = messageSender;
    }

    @Override
    protected void startRoutine(DiscordApi discordApi, Server server, TextChannel textChannel,
                                Message message, User user, Emoji emoji) throws MyOwnException {

        boolean buttonForUser = user.getIdAsString().equals(player.getId());
        if (!buttonForUser) {
            messageSender.send(new ButtonNotForYou(user.getMentionTag(), player.getNameTag()), textChannel
            );
            return;
        }

        boolean userIsOwner = player.getWaifuList().contains(waifu);
        if (!userIsOwner) {
            messageSender.send(new ButtonNotForYou(user.getMentionTag(), player.getNameTag()), textChannel
            );
            return;
        }

        if (emoji.equalsEmoji(Emojis.X.getEmoji())) {
            delete(message);
        } else if (emoji.equalsEmoji(Emojis.COOKIE.getEmoji())) {
            levelUp(textChannel, message, 1);
        } else if (emoji.equalsEmoji(Emojis.CUPCAKE.getEmoji())) {
            levelUp(textChannel, message, 10);
        } else if (emoji.equalsEmoji(Emojis.STAR2.getEmoji())) {
            riseRarity(textChannel, message);
        } else if (emoji.equalsEmoji(Emojis.HAT.getEmoji())) {
            changePicture(message);
        }

        playerLoader.savePlayer(player);
    }

    private void changePicture(Message message) throws MyOwnException {
        int cost = 1;

        Optional<String> randomPictureByMalId = jikanFetcher.getRandomPictureByMalId(waifu.getIdMal());
        if (randomPictureByMalId.isPresent()) {
            player.getInventory().removeMorphStones(cost);
            waifu.setImageUrl(randomPictureByMalId.get());
            message.edit(new WaifuEmbed(waifu));
            messageSender.send(new ChangedPicture(player, cost), message.getChannel());
        } else {
            messageSender.send(new ImageNotFound(waifu), message.getChannel());
        }

    }

    private void delete(Message message) throws MyOwnException {
        int newStardust = waifu.getRarity().getSellValue();
        int newCookies = waifu.getLevel();
        player.getInventory().addStardust(newStardust);
        player.getInventory().addCookies(newCookies);

        message.edit(new WaifuDeletedEmbed(player, waifu, newStardust, newCookies));
        message.removeAllReactions();

        waifuLoader.deleteWaifu(waifu, player);
        Terminal.printLine(player.getName() + " deleted waifu " + waifu.getName());
    }

    private void levelUp(TextChannel textChannel, Message message, int cookies)
            throws MyOwnException {

        if (waifu.getLevel() >= 100) {
            messageSender.send(new WaifuHasMaxLevel(), textChannel);
            return;
        }

        int newXP = Math.min((int) Math.pow(waifu.getLevel() + cookies, 3) - waifu.getXp(),
                cookies * 10000);
        player.getInventory().removeCookies(cookies);
        waifu.addXp(newXP);

        message.edit(new WaifuEmbed(waifu));
    }

    private void riseRarity(TextChannel textChannel, Message message) throws MyOwnException {

        if (waifu.getLevel() < waifu.getRarity().getNextRarity().getMinLevel()) {
            messageSender.send(new WaifuLevelTooLow(player, waifu), textChannel);
            return;
        }

        player.getInventory().removeStardust(waifu.getRarity().getUpgradeCost());
        waifu.increaseRarity();

        messageSender.send(new WaifuRarityIncreased(waifu, waifu.getRarity()), textChannel);
        message.edit(new WaifuEmbed(waifu));
    }


}
