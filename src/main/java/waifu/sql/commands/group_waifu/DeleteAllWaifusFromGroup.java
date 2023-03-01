package waifu.sql.commands.group_waifu;

import waifu.sql.commands.SQLCommandWithoutResult;
import waifu.model.Group;

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
