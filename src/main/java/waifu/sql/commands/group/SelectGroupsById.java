package waifu.sql.commands.group;

import waifu.sql.commands.SQLCommandWithResult;
import waifu.sql.entry.GroupEntrySet;

public class SelectGroupsById extends SQLCommandWithResult<GroupEntrySet> {

    private final String groupId;

    public SelectGroupsById(String groupId) {
        super(new GroupEntrySet());
        this.groupId = groupId;
    }

    @Override
    protected String getCommand() {
        return "select * from KRAMLSBOT.GROUP where id like " + "'" + groupId + "'" + ";";
    }
}
