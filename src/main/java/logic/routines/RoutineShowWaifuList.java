package logic.routines;

import domain.Answer;
import domain.PlayerLoader;
import domain.WaifuLoader;
import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import logic.messages.MessageSender;
import logic.waifu.JikanFetcher;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;
import util.Terminal;

public class RoutineShowWaifuList extends Routine {

  private final MessageSender messageSender;
  private final User user;
  private final PlayerLoader playerLoader;
  private final WaifuLoader waifuLoader;
  private final JikanFetcher jikanFetcher;
  private final TextChannel channel;
  private final Terminal terminal;

  public RoutineShowWaifuList(MessageSender messageSender, User user, PlayerLoader playerLoader,
      WaifuLoader waifuLoader, JikanFetcher jikanFetcher, TextChannel channel, Terminal terminal) {
    this.messageSender = messageSender;
    this.user = user;
    this.playerLoader = playerLoader;
    this.waifuLoader = waifuLoader;
    this.jikanFetcher = jikanFetcher;
    this.channel = channel;
    this.terminal = terminal;
  }

  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {
    Player player = playerLoader.getPlayerByUser(user);
    messageSender.sendWaifuList(channel, player, messageSender, playerLoader, waifuLoader,
        jikanFetcher, terminal);

    return new Answer("Someone showed his animeList");
  }
}
