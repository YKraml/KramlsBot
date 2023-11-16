package database.sql.commands.team;

import database.sql.commands.SQLCommandWithResult;
import database.sql.entry.TeamEntrySet;

public class SelectTeamsByDungeonChannelId extends SQLCommandWithResult<TeamEntrySet> {

    private final String dungeonChannelId;

    public SelectTeamsByDungeonChannelId(String dungeonChannelId) {
        super(new TeamEntrySet());
        this.dungeonChannelId = dungeonChannelId;
    }

    @Override
    protected String getCommand() {
        return "select * from KRAMLSBOT.TEAM where dungeon = '%s';".formatted(dungeonChannelId);
    }
}
