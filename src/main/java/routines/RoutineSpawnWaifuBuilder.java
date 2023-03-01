package routines;

import java.util.concurrent.ScheduledExecutorService;
import org.javacord.api.DiscordApi;
import waifu.WaifuBuilder;
import waifu.WaifuSpawnManager;

public class RoutineSpawnWaifuBuilder {

  private final ScheduledExecutorService scheduledExecutorService;
  private final WaifuSpawnManager waifuSpawnManager;
  private final WaifuBuilder waifuBuilder;
  private final DiscordApi discordApi;

  public RoutineSpawnWaifuBuilder(ScheduledExecutorService scheduledExecutorService,
      WaifuSpawnManager waifuSpawnManager, WaifuBuilder waifuBuilder, DiscordApi discordApi) {
    this.scheduledExecutorService = scheduledExecutorService;
    this.waifuSpawnManager = waifuSpawnManager;
    this.waifuBuilder = waifuBuilder;
    this.discordApi = discordApi;
  }

  public RoutineSpawnWaifu createRoutineSpawnWaifu() {
    return new RoutineSpawnWaifu(scheduledExecutorService, waifuSpawnManager, waifuBuilder,
        discordApi);
  }
}