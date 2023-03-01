package waifu.sql.commands.team;

import waifu.sql.commands.SQLCommandWithResult;
import waifu.sql.entry.TeamEntrySet;

public class SelectTeamsById extends SQLCommandWithResult<TeamEntrySet> {

    private final String teamId;

    public SelectTeamsById(String teamId) {
        super(new TeamEntrySet());
        this.teamId = teamId;
    }

    @Override
    protected String getCommand() {
        return "select * from KRAMLSBOT.TEAM where id like " + "'" + teamId + "'" + ";";
    }

}
