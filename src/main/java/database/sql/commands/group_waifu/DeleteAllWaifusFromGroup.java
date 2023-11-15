package database.sql.commands.group_waifu;

import database.sql.commands.SQLCommandWithoutResult;
import domain.waifu.Group;

public class DeleteAllWaifusFromGroup extends SQLCommandWithoutResult {

    private final Group group;

    public DeleteAllWaifusFromGroup(Group group) {
        this.group = group;
    }

    @Override
    protected String getCommand() {
        return "delete from KRAMLSBOT.GROUP_WAIFU where idGroup = " + group.getId() + ";";
    }
}
