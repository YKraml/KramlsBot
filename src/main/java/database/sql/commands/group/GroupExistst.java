package database.sql.commands.group;

import database.sql.commands.SQLCommandCheckExistence;
import domain.waifu.Group;

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
