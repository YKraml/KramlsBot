package logic.routines;

import domain.Answer;
import domain.DungeonLoader;
import domain.PlayerLoader;
import domain.TeamLoader;
import domain.exceptions.MyOwnException;
import domain.waifu.dungeon.Dungeon;
import domain.waifu.dungeon.Team;
import java.util.List;
import java.util.Optional;
import logic.discord.ChannelFinder;
import logic.messages.MessageSender;
import logic.waifu.DungeonTicker;
import org.javacord.api.entity.channel.TextChannel;

public class RoutineDungeonTick extends Routine {

  private final DungeonTicker dungeonTicker;
  private final ChannelFinder channelFinder;
  private final TeamLoader teamLoader;
  private final PlayerLoader playerLoader;
  private final DungeonLoader dungeonLoader;
  private final MessageSender messageSender;

  public RoutineDungeonTick(DungeonTicker dungeonTicker, ChannelFinder channelFinder,
      TeamLoader teamLoader, PlayerLoader playerLoader, DungeonLoader dungeonLoader,
      MessageSender messageSender) {
    this.dungeonTicker = dungeonTicker;
    this.channelFinder = channelFinder;
    this.teamLoader = teamLoader;
    this.playerLoader = playerLoader;
    this.dungeonLoader = dungeonLoader;
    this.messageSender = messageSender;
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

      textChannel.ifPresent(
          channel -> messageSender.sendSafeDungeonMessage(channel, dungeon, teams));
    }

    return new Answer("Dungeon tick.");
  }
}
