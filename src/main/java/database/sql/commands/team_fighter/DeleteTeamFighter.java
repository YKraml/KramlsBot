package database.sql.commands.team_fighter;

import domain.waifu.Waifu;
import database.sql.commands.SQLCommandWithoutResult;

public class DeleteTeamFighter extends SQLCommandWithoutResult {

    private final Waifu waifu;

    public DeleteTeamFighter(Waifu waifu) {
        this.waifu = waifu;
    }

    @Override
    protected String getCommand() {
        return "delete from KRAMLSBOT.TEAM_FIGHTER where KRAMLSBOT.TEAM_FIGHTER.idWaifu like " + "'"
            + waifu.getId() + "'" + ";";
    }
}
