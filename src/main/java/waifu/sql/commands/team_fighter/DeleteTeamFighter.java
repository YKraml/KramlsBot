package waifu.sql.commands.team_fighter;

import waifu.sql.commands.SQLCommandWithoutResult;
import waifu.model.Waifu;

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
