package routines;

import actions.listeners.commands.Answer;
import discord.ChannelFinder;
import embeds.dungeon.DungeonEmbed;
import exceptions.MyOwnException;
import java.util.List;
import java.util.Optional;
import org.javacord.api.entity.channel.TextChannel;
import waifu.loader.DungeonLoader;
import waifu.loader.PlayerLoader;
import waifu.loader.TeamLoader;
import waifu.model.dungeon.Dungeon;
import waifu.model.dungeon.DungeonTicker;
import waifu.model.dungeon.Team;

public class RoutineDungeonTick extends Routine {


  private final DungeonTicker dungeonTicker;
  private final ChannelFinder channelFinder;
  private final TeamLoader teamLoader;
  private final PlayerLoader playerLoader;
  private final DungeonLoader dungeonLoader;

  public RoutineDungeonTick(DungeonTicker dungeonTicker, ChannelFinder channelFinder,
      TeamLoader teamLoader, PlayerLoader playerLoader, DungeonLoader dungeonLoader) {
    this.dungeonTicker = dungeonTicker;
    this.channelFinder = channelFinder;
    this.teamLoader = teamLoader;
    this.playerLoader = playerLoader;
    this.dungeonLoader = dungeonLoader;
  }

  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {

    for (Dungeon dungeon : dungeonLoader.getAllDungeons()) {

      dungeonTicker.tick(dungeon);
      String channelId = dungeon.getChannelId();
      Optional<TextChannel> textChannel = channelFinder.getTextChannelById(channelId);
      List<Team> teams = teamLoader.getTeamsInDungeon(channelId, playerLoader);

      if (teams.isEmpty()) {
        continue;
      }
      textChannel.ifPresent(channel -> channel.sendMessage(new DungeonEmbed(dungeon, teams)));
    }

    return new Answer("Dungeon tick.");
  }
}
