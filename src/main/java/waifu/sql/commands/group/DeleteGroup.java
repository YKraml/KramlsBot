package waifu.sql.commands.group;

import waifu.sql.commands.SQLCommandWithoutResult;
import waifu.model.Group;

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
