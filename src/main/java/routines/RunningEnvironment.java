package routines;

import discord.ChannelFinder;
import java.util.concurrent.ScheduledExecutorService;
import messages.MessageSender;
import music.guess.GuessingGameManager;
import music.audio.MusicPlayerManager;
import waifu.JikanFetcher;
import waifu.WaifuSpawnManager;
import waifu.loader.DungeonLoader;
import waifu.loader.PlayerLoader;
import waifu.loader.TeamLoader;
import waifu.loader.WaifuLoader;
import waifu.model.dungeon.DungeonTicker;
import youtube.YoutubeFetcher;

public class RunningEnvironment {

  public final GuessingGameManager guessingGameManager;
  public final JikanFetcher jikanFetcher;
  public final YoutubeFetcher youtubeFetcher;
  public final ChannelFinder channelFinder;
  public final MusicPlayerManager musicPlayerManager;
  public final MessageSender messageSender;
  public final PlayerLoader playerLoader;
  public final DungeonTicker dungeonTicker;
  public final TeamLoader teamLoader;
  public final WaifuSpawnManager waifuSpawnManager;
  public final ScheduledExecutorService scheduledExecutorService;
  public final DungeonLoader dungeonLoader;
  public final WaifuLoader waifuLoader;

  public RunningEnvironment(GuessingGameManager guessingGameManager, JikanFetcher jikanFetcher,
      YoutubeFetcher youtubeFetcher, ChannelFinder channelFinder,
      MusicPlayerManager musicPlayerManager, MessageSender messageSender,
      PlayerLoader playerLoader, DungeonTicker dungeonTicker, TeamLoader teamLoader,
      WaifuSpawnManager waifuSpawnManager, ScheduledExecutorService scheduledExecutorService,
      DungeonLoader dungeonLoader, WaifuLoader waifuLoader) {
    this.guessingGameManager = guessingGameManager;
    this.jikanFetcher = jikanFetcher;
    this.youtubeFetcher = youtubeFetcher;
    this.channelFinder = channelFinder;
    this.musicPlayerManager = musicPlayerManager;
    this.messageSender = messageSender;
    this.playerLoader = playerLoader;
    this.dungeonTicker = dungeonTicker;
    this.teamLoader = teamLoader;
    this.waifuSpawnManager = waifuSpawnManager;
    this.scheduledExecutorService = scheduledExecutorService;
    this.dungeonLoader = dungeonLoader;
    this.waifuLoader = waifuLoader;
  }
}
