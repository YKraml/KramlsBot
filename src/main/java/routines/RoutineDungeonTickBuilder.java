package routines;

import com.google.inject.Inject;
import discord.ChannelFinder;
import waifu.loader.DungeonLoader;
import waifu.loader.PlayerLoader;
import waifu.loader.TeamLoader;
import waifu.model.dungeon.DungeonTicker;

public class RoutineDungeonTickBuilder {

  private final DungeonTicker dungeonTicker;
  private final ChannelFinder channelFinder;
  private final TeamLoader teamLoader;
  private final PlayerLoader playerLoader;
  private final DungeonLoader dungeonLoader;

  @Inject
  public RoutineDungeonTickBuilder(DungeonTicker dungeonTicker, ChannelFinder channelFinder,
      TeamLoader teamLoader, PlayerLoader playerLoader, DungeonLoader dungeonLoader) {
    this.dungeonTicker = dungeonTicker;
    this.channelFinder = channelFinder;
    this.teamLoader = teamLoader;
    this.playerLoader = playerLoader;
    this.dungeonLoader = dungeonLoader;
  }

  public RoutineDungeonTick createRoutineDungeonTick() {
    return new RoutineDungeonTick(dungeonTicker, channelFinder, teamLoader, playerLoader,
        dungeonLoader);
  }
}