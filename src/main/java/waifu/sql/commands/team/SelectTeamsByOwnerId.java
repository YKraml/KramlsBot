package waifu.sql.commands.team;

import waifu.sql.commands.SQLCommandWithResult;
import waifu.sql.entry.TeamEntrySet;

public class SelectTeamsByOwnerId extends SQLCommandWithResult<TeamEntrySet> {

    private final String playerId;

    public SelectTeamsByOwnerId(String playerId) {
        super(new TeamEntrySet());
        this.playerId = playerId;
    }

    @Override
    protected String getCommand() {
        return "select * from KRAMLSBOT.TEAM where player = '%s';".formatted(playerId);
    }
}
