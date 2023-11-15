package ui.reaction;

import ui.embeds.group.GroupsListEmbed;
import domain.exceptions.MyOwnException;
import ui.messages.MessageSender;
import ui.messages.MessageSenderImpl;
import ui.messages.messages.GroupNotFound;
import ui.messages.messages.GroupOverview;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import logic.waifu.JikanFetcher;
import logic.waifu.PlayerLoader;
import logic.waifu.WaifuLoader;
import domain.waifu.Group;
import domain.waifu.Player;

public class GroupListListener extends MyAbstractListListener<Group> {

  private final Player player;
  private final PlayerLoader playerLoader;
  private final WaifuLoader waifuLoader;
  private final JikanFetcher jikanFetcher;
  private final MessageSender messageSender;

  public GroupListListener(Player player, PlayerLoader playerLoader, WaifuLoader waifuLoader,
      JikanFetcher jikanFetcher, MessageSender messageSender) {
    super(player.getGroupList());
    this.player = player;
    this.playerLoader = playerLoader;
    this.waifuLoader = waifuLoader;
    this.jikanFetcher = jikanFetcher;
    this.messageSender = messageSender;
  }

  @Override
  protected void updateMessage(Message message, int page) {
    message.edit(new GroupsListEmbed(player, page));
  }

  @Override
  protected void reactToCountEmoji(Server server, TextChannel textChannel, User user,
      int listPosition) throws MyOwnException {
    Group group = player.getGroupList().get(listPosition);
    MessageSenderImpl result;
    synchronized (MessageSenderImpl.class) {
      result = new MessageSenderImpl();
    }
    result.send(new GroupOverview(group, player, playerLoader,
        waifuLoader, jikanFetcher, messageSender), textChannel);
  }

  @Override
  protected void reactToTooHighCountEmoji(TextChannel textChannel, int listPosition)
      throws MyOwnException {
    MessageSenderImpl result;
    synchronized (MessageSenderImpl.class) {
      result = new MessageSenderImpl();
    }
    result.send(new GroupNotFound(), textChannel);
  }

}
