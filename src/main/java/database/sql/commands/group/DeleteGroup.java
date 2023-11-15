package database.sql.commands.group;

import database.sql.commands.SQLCommandWithoutResult;
import domain.waifu.Group;

public class DeleteGroup extends SQLCommandWithoutResult {

    private final Group group;

    public DeleteGroup(Group group) {
        this.group = group;
    }

    @Override
    protected String getCommand() {
        return "delete from KRAMLSBOT.GROUP where id like " + group.getId() + ";";
    }
}
