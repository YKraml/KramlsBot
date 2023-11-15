package database.sql.commands.group_waifu;

import domain.waifu.Group;
import domain.waifu.Waifu;
import database.sql.commands.SQLCommandCheckExistence;

public class GroupWaifuExists extends SQLCommandCheckExistence {

    private final Group group;
    private final Waifu waifu;

    public GroupWaifuExists(Group group, Waifu waifu) {
        this.group = group;
        this.waifu = waifu;
    }

    @Override
    protected String getCommand() {
        return "select * from KRAMLSBOT.GROUP_WAIFU where " +
                "idGroup like " + "'" + group.getId() + "'" + " and " +
                "idWaifu like " + "'" + waifu.getId() + "'" + ";";
    }
}
