package actions.timer;

import discord.ChannelFinder;
import java.util.concurrent.ScheduledExecutorService;
import org.javacord.api.DiscordApi;
import routines.RoutineRunner;
import waifu.WaifuSpawnManager;
import waifu.loader.DungeonLoader;
import waifu.loader.PlayerLoader;
import waifu.loader.TeamLoader;
import waifu.model.dungeon.DungeonTicker;

public class TimerManager {

  private final DiscordApi api;
  private final ScheduledExecutorService scheduledExecutorService;
  private final DungeonLoader dungeonLoader;
  private final RoutineRunner routineRunner;
  private final DiscordApi discordApi;
  private final DungeonTicker dungeonTicker;
  private final ChannelFinder channelFinder;
  private final TeamLoader teamLoader;
  private final PlayerLoader playerLoader;
  private final WaifuSpawnManager waifuSpawnManager;

  public TimerManager(DiscordApi api, ScheduledExecutorService scheduledExecutorService,
      DungeonLoader dungeonLoader,
      RoutineRunner routineRunner,
      DiscordApi discordApi, DungeonTicker dungeonTicker, ChannelFinder channelFinder,
      TeamLoader teamLoader, PlayerLoader playerLoader, WaifuSpawnManager waifuSpawnManager) {
    this.api = api;
    this.scheduledExecutorService = scheduledExecutorService;
    this.dungeonLoader = dungeonLoader;
    this.routineRunner = routineRunner;
    this.discordApi = discordApi;
    this.dungeonTicker = dungeonTicker;
    this.channelFinder = channelFinder;
    this.teamLoader = teamLoader;
    this.playerLoader = playerLoader;
    this.waifuSpawnManager = waifuSpawnManager;
  }


}
