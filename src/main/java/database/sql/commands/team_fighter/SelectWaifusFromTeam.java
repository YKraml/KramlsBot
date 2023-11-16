package database.sql.commands.team_fighter;

import database.sql.commands.SQLCommandWithResult;
import database.sql.entry.TeamFighterEntrySet;
import domain.waifu.dungeon.Team;

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
