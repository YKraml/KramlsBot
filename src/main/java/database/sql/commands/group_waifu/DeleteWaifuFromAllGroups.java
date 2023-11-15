package database.sql.commands.group_waifu;

import database.sql.commands.SQLCommandWithoutResult;
import domain.waifu.Waifu;

public class DeleteWaifuFromAllGroups extends SQLCommandWithoutResult {

    private final Waifu waifu;

    public DeleteWaifuFromAllGroups(Waifu waifu) {
        this.waifu = waifu;
    }

    @Override
    protected String getCommand() {
        return "delete from KRAMLSBOT.GROUP_WAIFU where KRAMLSBOT.GROUP_WAIFU.idWaifu like " + "'" + waifu.getId() + "'"
            + ";";
    }
}
