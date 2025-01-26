package logic.routines;

import com.google.inject.Inject;
import domain.PlayerLoader;
import org.javacord.api.DiscordApi;
import util.Terminal;

public class RoutineGivePointsBuilder {

  private final PlayerLoader playerLoader;
  private final DiscordApi api;
  private final Terminal terminal;

  @Inject
  public RoutineGivePointsBuilder(PlayerLoader playerLoader, DiscordApi api, Terminal terminal) {
    this.playerLoader = playerLoader;
    this.api = api;
    this.terminal = terminal;
  }


  public RoutineGivePoints createRoutineGivePoints(int moneyPerMinute, int passedTimeInMinutes) {
    return new RoutineGivePoints(moneyPerMinute, passedTimeInMinutes, playerLoader, api, terminal);
  }
}