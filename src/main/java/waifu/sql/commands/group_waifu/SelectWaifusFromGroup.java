package waifu.sql.commands.group_waifu;

import waifu.sql.entry.GroupWaifuEntrySet;
import waifu.model.Group;
import waifu.sql.commands.SQLCommandWithResult;

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
