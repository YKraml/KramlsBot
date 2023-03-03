package routines;

import actions.listeners.commands.Answer;
import exceptions.MyOwnException;
import messages.MessageSender;
import messages.messages.GroupList;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;
import waifu.JikanFetcher;
import waifu.loader.PlayerLoader;
import waifu.loader.WaifuLoader;
import waifu.model.Player;

public class RoutineShowGroupList extends Routine {

  private final MessageSender messageSender;
  private final User user;
  private final PlayerLoader playerLoader;
  private final WaifuLoader waifuLoader;
  private final JikanFetcher jikanFetcher;
  private final TextChannel channel;

  public RoutineShowGroupList(MessageSender messageSender, User user, PlayerLoader playerLoader,
      WaifuLoader waifuLoader, JikanFetcher jikanFetcher, TextChannel channel) {
    this.messageSender = messageSender;
    this.user = user;
    this.playerLoader = playerLoader;
    this.waifuLoader = waifuLoader;
    this.jikanFetcher = jikanFetcher;
    this.channel = channel;
  }

  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {
    Player player = playerLoader.getPlayerByUser(user);
    messageSender.send(
        new GroupList(player, playerLoader, waifuLoader, jikanFetcher, messageSender), channel);

    return new Answer("Showed someone his list.");
  }
}
