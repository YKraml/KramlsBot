package routines;

import actions.listeners.commands.Answer;
import exceptions.MyOwnException;
import messages.MessageSender;
import messages.messages.WaifuList;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;
import waifu.JikanFetcher;
import waifu.loader.PlayerLoader;
import waifu.loader.WaifuLoader;
import waifu.model.Player;

public class RoutineShowWaifuList extends Routine {

  private final MessageSender messageSender;
  private final User user;
  private final PlayerLoader playerLoader;
  private final WaifuLoader waifuLoader;
  private final JikanFetcher jikanFetcher;
  private final TextChannel channel;

  public RoutineShowWaifuList(MessageSender messageSender, User user, PlayerLoader playerLoader,
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
    messageSender.send(new WaifuList(player, messageSender, playerLoader, waifuLoader, jikanFetcher), channel);

    return new Answer("Someone showed his animeList");
  }
}
