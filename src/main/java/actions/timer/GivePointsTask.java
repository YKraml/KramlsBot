package actions.timer;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.server.Server;
import routines.RoutineGivePoints;
import routines.RoutineRunner;
import waifu.loader.PlayerLoader;

public class GivePointsTask extends SaveRunnable {

  private static final int MONEY_PER_MINUTE = 10;
  private static final int TIMER = 5;
  private final DiscordApi api;
  private final RoutineRunner routineRunner;
  private final PlayerLoader playerLoader;

  public GivePointsTask(DiscordApi api, RoutineRunner routineRunner, PlayerLoader playerLoader) {
    this.api = api;
    this.routineRunner = routineRunner;
    this.playerLoader = playerLoader;
  }

  @Override
  public void runSave() throws Exception {

    for (Server server : api.getServers()) {
      for (ServerVoiceChannel voiceChannel : server.getVoiceChannels()) {
        for (Long userId : voiceChannel.getConnectedUserIds()) {
          routineRunner.startRoutine(
              new RoutineGivePoints(server, userId, MONEY_PER_MINUTE, TIMER, playerLoader));
        }
      }
    }
  }

}


