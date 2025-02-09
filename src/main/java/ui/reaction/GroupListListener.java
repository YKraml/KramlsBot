package ui.reaction;

import domain.PlayerLoader;
import domain.WaifuLoader;
import domain.exceptions.MyOwnException;
import domain.waifu.Group;
import domain.waifu.Player;
import logic.messages.MessageSender;
import logic.waifu.JikanFetcher;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import ui.embeds.group.GroupsListEmbed;
import ui.messages.messages.GroupNotFound;
import ui.messages.messages.GroupOverview;
import util.Terminal;

public class GroupListListener extends MyAbstractListListener<Group> {

  private final Player player;
  private final PlayerLoader playerLoader;
  private final WaifuLoader waifuLoader;
  private final JikanFetcher jikanFetcher;
  private final MessageSender messageSender;
  private final Terminal terminal;

  public GroupListListener(Player player, PlayerLoader playerLoader, WaifuLoader waifuLoader,
      JikanFetcher jikanFetcher, MessageSender messageSender, Terminal terminal) {
    super(player.getGroupList());
    this.player = player;
    this.playerLoader = playerLoader;
    this.waifuLoader = waifuLoader;
    this.jikanFetcher = jikanFetcher;
    this.messageSender = messageSender;
    this.terminal = terminal;
  }

  @Override
  protected void updateMessage(Message message, int page) {
    message.edit(new GroupsListEmbed(player, page));
  }

  @Override
  protected void reactToCountEmoji(Server server, TextChannel textChannel, User user,
      int listPosition) throws MyOwnException {
    Group group = player.getGroupList().get(listPosition);
    messageSender.send(new GroupOverview(group, player, playerLoader,
        waifuLoader, jikanFetcher, messageSender, terminal), textChannel);
  }

  @Override
  protected void reactToTooHighCountEmoji(TextChannel textChannel, int listPosition)
      throws MyOwnException {
    messageSender.send(new GroupNotFound(), textChannel);
  }

}
