package database.sql.commands.group;

import database.sql.commands.SQLCommandWithResult;
import database.sql.entry.GroupEntrySet;

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
