package waifu.sql.commands.team_fighter;

import waifu.model.dungeon.Team;
import waifu.sql.commands.SQLCommandWithResult;
import waifu.sql.entry.TeamFighterEntrySet;

public class SelectWaifusFromTeam extends SQLCommandWithResult<TeamFighterEntrySet> {

    private final Team team;

    public SelectWaifusFromTeam(Team team) {
        super(new TeamFighterEntrySet());
        this.team = team;
    }

    @Override
    protected String getCommand() {
        return "select * from KRAMLSBOT.TEAM_FIGHTER where idTeam like " + "'" + team.getId() + "'"
            + ";";
    }
}
