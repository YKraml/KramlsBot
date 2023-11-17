package ui.reaction;

import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import domain.waifu.Waifu;
import logic.MessageSender;
import logic.waifu.WaifuLoader;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import ui.embeds.waifu.DeleteWaifuListEmbed;
import ui.messages.MessageSenderImpl;
import ui.messages.messages.ButtonNotForYou;
import ui.messages.messages.WaifuDeleted;
import ui.messages.messages.WaifuNotFound;
import util.Emojis;

public class DeleteWaifuListListener extends MyAbstractListListener<Waifu> {

    private final Player player;
    private final WaifuLoader waifuLoader;
    private final MessageSender messageSender;
    private int sortDirection = 1;

    public DeleteWaifuListListener(Player player, WaifuLoader waifuLoader,
                                   MessageSender messageSender) {
        super(player.getWaifuList());
        this.player = player;
        this.waifuLoader = waifuLoader;
        this.messageSender = messageSender;
    }

    @Override
    protected void updateMessage(Message message, int page) {
        message.edit(new DeleteWaifuListEmbed(player, page));
    }

    @Override
    protected void reactToCountEmoji(Server server, TextChannel textChannel, User user,
                                     int listPosition) throws MyOwnException {

        Waifu waifu = player.getWaifuList().get(listPosition);

        int stardust = waifu.getRarity().getSellValue();
        int cookies = waifu.getLevel();

        player.getInventory().addStardust(stardust);
        player.getInventory().addCookies(cookies);

        waifuLoader.deleteWaifu(waifu, player);

        MessageSenderImpl result;
        synchronized (MessageSenderImpl.class) {
            result = new MessageSenderImpl();
        }
        result.send(new WaifuDeleted(player, waifu, stardust, cookies), textChannel);

    }

    @Override
    protected void reactToTooHighCountEmoji(TextChannel textChannel, int listPosition)
            throws MyOwnException {
        MessageSenderImpl result;
        synchronized (MessageSenderImpl.class) {
            result = new MessageSenderImpl();
        }
        result.send(new WaifuNotFound(listPosition), textChannel);
    }

    protected void startRoutine(DiscordApi discordApi, Server server, TextChannel textChannel,
                                Message message, User user, Emoji emoji) throws MyOwnException {

        if (!user.getIdAsString().equals(player.getId())) {
            messageSender.send(new ButtonNotForYou(user.getMentionTag(), player.getNameTag()), textChannel
            );
            return;
        }

        super.startRoutine(discordApi, server, textChannel, message, user, emoji);

        if (emoji.equalsEmoji(Emojis.COOKIE.getEmoji())) {
            sortDirection *= -1;
            player.sortWaifuList((o1, o2) -> (o2.getStatsSum() - o1.getStatsSum()) * sortDirection);
            this.updateMessage(message, super.getPageNumber());
        } else if (emoji.equalsEmoji(Emojis.STAR2.getEmoji())) {
            sortDirection *= -1;
            player.sortWaifuList((o1, o2) -> (o2.getRarity().getStatsSum() - o1.getRarity().getStatsSum())
                    * sortDirection);
            this.updateMessage(message, super.getPageNumber());
        } else if (emoji.equalsEmoji(Emojis.ABC.getEmoji())) {
            sortDirection *= -1;
            player.sortWaifuList((o1, o2) -> {
                int com = (o2.getAnimeName().compareTo(o1.getAnimeName())) * sortDirection;
                if (com == 0) {
                    com = o2.getName().compareTo(o1.getName());
                }
                return com;
            });
            this.updateMessage(message, super.getPageNumber());
        }
    }
}
