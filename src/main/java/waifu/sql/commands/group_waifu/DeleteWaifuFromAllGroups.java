package waifu.sql.commands.group_waifu;

import waifu.sql.commands.SQLCommandWithoutResult;
import waifu.model.Waifu;

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
