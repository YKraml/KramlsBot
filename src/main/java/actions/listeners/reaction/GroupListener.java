package actions.listeners.reaction;

import embeds.group.GroupEmbed;
import exceptions.MyOwnException;
import java.util.ArrayList;
import messages.MessageSender;
import messages.messages.WaifuNotFound;
import messages.messages.WaifuStats;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.listener.message.reaction.ReactionAddListener;
import waifu.JikanFetcher;
import waifu.loader.PlayerLoader;
import waifu.loader.WaifuLoader;
import waifu.model.Group;
import waifu.model.Player;
import waifu.model.Waifu;

public class GroupListener extends MyAbstractListListener<Waifu> implements ReactionAddListener {

  private final Group group;
  private final Player player;
  private final PlayerLoader playerLoader;
  private final WaifuLoader waifuLoader;
  private final JikanFetcher jikanFetcher;
  private final MessageSender messageSender;

  public GroupListener(Group group, Player player, PlayerLoader playerLoader,
      WaifuLoader waifuLoader, JikanFetcher jikanFetcher, MessageSender messageSender) {
    super(new ArrayList<>(group.getWaifuList()));
    this.group = group;
    this.player = player;
    this.playerLoader = playerLoader;
    this.waifuLoader = waifuLoader;
    this.jikanFetcher = jikanFetcher;
    this.messageSender = messageSender;
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
        waifuLoader, jikanFetcher, messageSender), textChannel);
  }

  @Override
  protected void reactToTooHighCountEmoji(TextChannel textChannel, int listPosition)
      throws MyOwnException {
    messageSender.send(new WaifuNotFound(listPosition), textChannel);
  }
}
