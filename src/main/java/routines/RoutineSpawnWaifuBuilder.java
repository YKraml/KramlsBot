package routines;

import com.google.inject.Inject;
import java.util.concurrent.ScheduledExecutorService;
import messages.MessageSender;
import org.javacord.api.DiscordApi;
import waifu.WaifuBuilder;
import waifu.WaifuSpawnManager;

public class RoutineSpawnWaifuBuilder {

  private final ScheduledExecutorService scheduledExecutorService;
  private final WaifuSpawnManager waifuSpawnManager;
  private final WaifuBuilder waifuBuilder;
  private final DiscordApi discordApi;
  private final MessageSender messageSender;

  @Inject
  public RoutineSpawnWaifuBuilder(ScheduledExecutorService scheduledExecutorService,
      WaifuSpawnManager waifuSpawnManager, WaifuBuilder waifuBuilder, DiscordApi discordApi,
      MessageSender messageSender) {
    this.scheduledExecutorService = scheduledExecutorService;
    this.waifuSpawnManager = waifuSpawnManager;
    this.waifuBuilder = waifuBuilder;
    this.discordApi = discordApi;
    this.messageSender = messageSender;
  }

  public RoutineSpawnWaifu createRoutineSpawnWaifu() {
    return new RoutineSpawnWaifu(scheduledExecutorService, waifuSpawnManager, waifuBuilder,
        discordApi, messageSender);
  }
}