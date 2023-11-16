package logic.routines;

import com.google.inject.Inject;
import domain.waifu.dungeon.DungeonTicker;
import logic.MessageSender;
import logic.waifu.DungeonLoader;
import logic.waifu.PlayerLoader;
import logic.waifu.TeamLoader;
import util.ChannelFinder;

public class RoutineDungeonTickBuilder {

    private final DungeonTicker dungeonTicker;
    private final ChannelFinder channelFinder;
    private final TeamLoader teamLoader;
    private final PlayerLoader playerLoader;
    private final DungeonLoader dungeonLoader;
    private final MessageSender messageSender;

    @Inject
    public RoutineDungeonTickBuilder(DungeonTicker dungeonTicker, ChannelFinder channelFinder, TeamLoader teamLoader, PlayerLoader playerLoader, DungeonLoader dungeonLoader, MessageSender messageSender) {
        this.dungeonTicker = dungeonTicker;
        this.channelFinder = channelFinder;
        this.teamLoader = teamLoader;
        this.playerLoader = playerLoader;
        this.dungeonLoader = dungeonLoader;
        this.messageSender = messageSender;
    }

    public RoutineDungeonTick createRoutineDungeonTick() {
        return new RoutineDungeonTick(dungeonTicker, channelFinder, teamLoader, playerLoader, dungeonLoader, messageSender);
    }
}