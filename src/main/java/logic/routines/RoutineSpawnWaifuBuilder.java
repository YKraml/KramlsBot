package logic.routines;

import com.google.inject.Inject;
import java.util.concurrent.ScheduledExecutorService;
import logic.messages.MessageSender;
import logic.waifu.WaifuBuilder;
import logic.waifu.WaifuSpawnManager;
import org.javacord.api.DiscordApi;
import util.Terminal;

public class RoutineSpawnWaifuBuilder {

  private final ScheduledExecutorService scheduledExecutorService;
  private final WaifuSpawnManager waifuSpawnManager;
  private final WaifuBuilder waifuBuilder;
  private final DiscordApi discordApi;
  private final MessageSender messageSender;
  private final Terminal terminal;

  @Inject
  public RoutineSpawnWaifuBuilder(ScheduledExecutorService scheduledExecutorService,
      WaifuSpawnManager waifuSpawnManager, WaifuBuilder waifuBuilder, DiscordApi discordApi,
      MessageSender messageSender, Terminal terminal) {
    this.scheduledExecutorService = scheduledExecutorService;
    this.waifuSpawnManager = waifuSpawnManager;
    this.waifuBuilder = waifuBuilder;
    this.discordApi = discordApi;
    this.messageSender = messageSender;
    this.terminal = terminal;
  }

  public RoutineSpawnWaifu createRoutineSpawnWaifu() {
    return new RoutineSpawnWaifu(scheduledExecutorService, waifuSpawnManager, waifuBuilder,
        discordApi, messageSender, terminal);
  }
}