package actions.listeners.reaction;

import actions.listeners.reaction.util.MyAbstractReactionListener;
import de.kraml.Terminal;
import discord.Emojis;
import embeds.waifu.WaifuDeletedEmbed;
import embeds.waifu.WaifuEmbed;
import exceptions.MyOwnException;
import messages.MessageSender;
import messages.MessageSenderImpl;
import messages.messages.ButtonNotForYou;
import messages.messages.NotEnoughStardust;
import messages.messages.WaifuHasMaxLevel;
import messages.messages.WaifuLEvelTooLow;
import messages.messages.WaifuRarityIncreased;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.listener.message.reaction.ReactionAddListener;
import waifu.JikanFetcher;
import waifu.loader.PlayerLoader;
import waifu.loader.WaifuLoader;
import waifu.model.Player;
import waifu.model.Waifu;

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
      messageSender.send(new ButtonNotForYou(user.getMentionTag(), player.getNameTag()),textChannel);
      return;
    }

    boolean userIsOwner = player.getWaifuList().contains(waifu);
    if (!userIsOwner) {
      messageSender.send(new ButtonNotForYou(user.getMentionTag(), player.getNameTag()),textChannel);
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
    } else if (emoji.equalsEmoji(Emojis.RING.getEmoji())) {
      makeBattleWaifu(textChannel);
    } else if (emoji.equalsEmoji(Emojis.HAT.getEmoji())) {
      changePicture(message);
    }

    playerLoader.savePlayer(player);
  }

  private void changePicture(Message message) throws MyOwnException {
    player.getInventory().removeMoney(1000);
    String randomPictureFrom = jikanFetcher.getRandomPictureFrom(waifu.getIdMal());
    waifu.setImageUrl(randomPictureFrom);
    message.edit(new WaifuEmbed(waifu));
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

  private void levelUp(TextChannel textChannel, Message message, int cookies) throws MyOwnException {

    if (waifu.getLevel() >= 100) {
      MessageSenderImpl result;
      synchronized (MessageSenderImpl.class) {
        result = new MessageSenderImpl();
      }
      result.send(new WaifuHasMaxLevel(),textChannel);
      return;
    }

    int newXP = Math.min((int) Math.pow(waifu.getLevel() + cookies, 3) - waifu.getXp(), cookies * 10000);
    player.getInventory().removeCookies(cookies);
    waifu.addXp(newXP);

    message.edit(new WaifuEmbed(waifu));
  }

  private void riseRarity(TextChannel textChannel, Message message) throws MyOwnException {
    if (!player.getInventory().hasStardust(waifu.getRarity().getUpgradeCost())) {
      messageSender.send(new NotEnoughStardust(player, waifu.getRarity().getUpgradeCost()), textChannel);
      return;
    }

    if (waifu.getLevel() < waifu.getRarity().getNextRarity().getMinLevel()) {
      messageSender.send(new WaifuLEvelTooLow(player, waifu), textChannel);
      return;
    }

    player.getInventory().removeStardust(waifu.getRarity().getUpgradeCost());
    waifu.increaseRarity();

    messageSender.send(new WaifuRarityIncreased(waifu, waifu.getRarity()), textChannel);
    message.edit(new WaifuEmbed(waifu));
  }

  private void makeBattleWaifu(TextChannel textChannel) {
    player.setBattleWaifu(waifu);
    textChannel.sendMessage("%s, '%s' ist deine Waifu zum kaempfen.".formatted(player.getName(), waifu.getName()));
  }


}
