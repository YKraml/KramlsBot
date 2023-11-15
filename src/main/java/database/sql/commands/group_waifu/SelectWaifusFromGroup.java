package database.sql.commands.group_waifu;

import database.sql.commands.SQLCommandWithResult;
import domain.waifu.Group;
import database.sql.entry.GroupWaifuEntrySet;

public class SelectWaifusFromGroup extends SQLCommandWithResult<GroupWaifuEntrySet> {

    private final Group group;

    public SelectWaifusFromGroup(Group group) {
        super(new GroupWaifuEntrySet());
        this.group = group;
    }

    @Override
    protected String getCommand() {
        return "select * from KRAMLSBOT.GROUP_WAIFU where idGroup like " + "'" + group.getId() + "'"
            + ";";
    }
}
