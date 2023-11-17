package logic.routines;

import domain.Answer;
import domain.exceptions.MyOwnException;
import domain.waifu.dungeon.Dungeon;
import domain.waifu.dungeon.Team;
import logic.ChannelFinder;
import logic.MessageSender;
import logic.waifu.DungeonLoader;
import logic.waifu.DungeonTicker;
import logic.waifu.PlayerLoader;
import logic.waifu.TeamLoader;
import org.javacord.api.entity.channel.TextChannel;

import java.util.List;
import java.util.Optional;

public class RoutineDungeonTick extends Routine {

    private final DungeonTicker dungeonTicker;
    private final ChannelFinder channelFinder;
    private final TeamLoader teamLoader;
    private final PlayerLoader playerLoader;
    private final DungeonLoader dungeonLoader;
    private final MessageSender messageSender;

    public RoutineDungeonTick(DungeonTicker dungeonTicker, ChannelFinder channelFinder, TeamLoader teamLoader, PlayerLoader playerLoader, DungeonLoader dungeonLoader, MessageSender messageSender) {
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

            textChannel.ifPresent(channel -> messageSender.sendSafeDungeonMessage(channel, dungeon, teams));
        }

        return new Answer("Dungeon tick.");
    }
}
