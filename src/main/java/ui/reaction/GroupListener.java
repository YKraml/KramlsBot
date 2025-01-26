package ui.reaction;

import domain.PlayerLoader;
import domain.WaifuLoader;
import domain.exceptions.MyOwnException;
import domain.waifu.Group;
import domain.waifu.Player;
import domain.waifu.Waifu;
import java.util.ArrayList;
import logic.messages.MessageSender;
import logic.waifu.JikanFetcher;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.listener.message.reaction.ReactionAddListener;
import ui.embeds.group.GroupEmbed;
import ui.messages.messages.WaifuNotFound;
import ui.messages.messages.WaifuStats;
import util.Terminal;

public class GroupListener extends MyAbstractListListener<Waifu> implements ReactionAddListener {

  private final Group group;
  private final Player player;
  private final PlayerLoader playerLoader;
  private final WaifuLoader waifuLoader;
  private final JikanFetcher jikanFetcher;
  private final MessageSender messageSender;
  private final Terminal terminal;

  public GroupListener(Group group, Player player, PlayerLoader playerLoader,
      WaifuLoader waifuLoader, JikanFetcher jikanFetcher, MessageSender messageSender,
      Terminal terminal) {
    super(new ArrayList<>(group.getWaifuList()));
    this.group = group;
    this.player = player;
    this.playerLoader = playerLoader;
    this.waifuLoader = waifuLoader;
    this.jikanFetcher = jikanFetcher;
    this.messageSender = messageSender;
    this.terminal = terminal;
  }

  @Override
  protected void updateMessage(Message message, int page) throws MyOwnException {
    message.edit(new GroupEmbed(group, player, page));
  }

  @Override
  protected void reactToCountEmoji(Server server, TextChannel textChannel, User user,
      int listPosition)
      throws MyOwnException {
    Waifu waifu = group.getWaifuList().get(listPosition);
    messageSender.send(new WaifuStats(waifu, player, playerLoader,
        waifuLoader, jikanFetcher, messageSender, terminal), textChannel);
  }

  @Override
  protected void reactToTooHighCountEmoji(TextChannel textChannel, int listPosition)
      throws MyOwnException {
    messageSender.send(new WaifuNotFound(listPosition), textChannel);
  }
}
