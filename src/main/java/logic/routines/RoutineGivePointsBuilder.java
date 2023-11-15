package logic.routines;

import com.google.inject.Inject;
import org.javacord.api.DiscordApi;
import logic.waifu.PlayerLoader;

public class RoutineGivePointsBuilder {

  private final PlayerLoader playerLoader;
  private final DiscordApi api;

  @Inject
  public RoutineGivePointsBuilder(PlayerLoader playerLoader, DiscordApi api) {
    this.playerLoader = playerLoader;
    this.api = api;
  }


  public RoutineGivePoints createRoutineGivePoints(int moneyPerMinute, int passedTimeInMinutes) {
    return new RoutineGivePoints(moneyPerMinute, passedTimeInMinutes, playerLoader, api);
  }
}