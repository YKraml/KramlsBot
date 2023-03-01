package waifu.sql.commands.group;

import waifu.model.Group;
import waifu.sql.commands.SQLCommandCheckExistence;

public class GroupExistst extends SQLCommandCheckExistence {

    private final Group group;

    public GroupExistst(Group group) {
        this.group = group;
    }

    @Override
    protected String getCommand() {
        return "select * from " + ("KRAMLSBOT" + "." + "GROUP") + " where id like " + "'" + group.getId() + "'" + ";";
    }
}
