package database.sql.commands.group_waifu;

import database.sql.commands.SQLCommandWithResult;
import database.sql.entry.GroupWaifuEntrySet;
import domain.waifu.Group;

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
