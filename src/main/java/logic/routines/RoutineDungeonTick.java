package logic.routines;

import domain.Answer;
import domain.exceptions.MyOwnException;
import domain.waifu.dungeon.Dungeon;
import domain.waifu.dungeon.DungeonTicker;
import domain.waifu.dungeon.Team;
import logic.waifu.DungeonLoader;
import logic.waifu.PlayerLoader;
import logic.waifu.TeamLoader;
import org.javacord.api.entity.channel.TextChannel;
import ui.embeds.dungeon.DungeonEmbed;
import util.ChannelFinder;

import java.util.List;
import java.util.Optional;

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
