package actions.listeners.reaction;

import actions.listeners.reaction.util.MyAbstractReactionListener;
import embeds.group.GroupEmbed;
import exceptions.MyOwnException;
import messages.MessageSender;
import messages.MessageSenderImpl;
import messages.messages.WaifuNotFound;
import messages.messages.WaifuStats;
import discord.Emojis;
import waifu.JikanFetcher;
import waifu.loader.PlayerLoader;
import waifu.loader.WaifuLoader;
import waifu.model.Group;
import waifu.model.Player;
import waifu.model.Waifu;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.listener.message.reaction.ReactionAddListener;

import java.util.ArrayList;
import java.util.List;

public class GroupListener extends MyAbstractReactionListener implements ReactionAddListener {

  private final Group group;
  private final Player player;
  private int pageNumber;
  private final int maxPages;
  private final PlayerLoader playerLoader;
  private final WaifuLoader waifuLoader;
  private final JikanFetcher jikanFetcher;
  private final MessageSender messageSender;

  public GroupListener(Group group, Player player, PlayerLoader playerLoader,
      WaifuLoader waifuLoader, JikanFetcher jikanFetcher, MessageSender messageSender) {
    this.group = group;
    this.player = player;
    this.playerLoader = playerLoader;
    this.waifuLoader = waifuLoader;
    this.jikanFetcher = jikanFetcher;
    this.messageSender = messageSender;
    this.pageNumber = 0;
    this.maxPages = group.getWaifuSet().size() / 10 + 1;
  }


  @Override
  protected void startRoutine(DiscordApi discordApi, Server server, TextChannel textChannel,
      Message message, User user, Emoji emoji) throws MyOwnException {

    if (emoji.equalsEmoji(Emojis.REWIND.getEmoji())) {
      pageNumber--;
      pageNumber = (pageNumber + maxPages) % maxPages;
      message.edit(new GroupEmbed(group, player, pageNumber));
    }
    if (emoji.equalsEmoji(Emojis.FAST_FORWARD.getEmoji())) {
      pageNumber++;
      pageNumber = (pageNumber + maxPages) % maxPages;
      message.edit(new GroupEmbed(group, player, pageNumber));
    }

    List<Waifu> waifusList = new ArrayList<>(group.getWaifuSet());
    for (int i = 0; i < 10; i++) {
      if (!emoji.equalsEmoji(Emojis.getCountEmojis()[i])) {
        continue;
      }

      Waifu waifu;
      try {
        waifu = waifusList.get(i + 10 * pageNumber);
        MessageSenderImpl result;
        synchronized (MessageSenderImpl.class) {
          result = new MessageSenderImpl();
        }
        result.send(new WaifuStats(waifu, player, playerLoader,
            waifuLoader, jikanFetcher, messageSender), textChannel);
      } catch (IndexOutOfBoundsException e) {
        MessageSenderImpl result;
        synchronized (MessageSenderImpl.class) {
          result = new MessageSenderImpl();
        }
        result.send(new WaifuNotFound(i + 10 * pageNumber), textChannel);
      }

      return;

    }

  }
}
