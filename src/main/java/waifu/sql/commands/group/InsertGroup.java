package waifu.sql.commands.group;

import waifu.sql.commands.SQLCommandWithoutResult;
import waifu.model.Group;
import waifu.model.Player;

public class InsertGroup extends SQLCommandWithoutResult {

    private final Group group;
    private final Player player;

    public InsertGroup(Group group, Player player) {
        this.group = group;
        this.player = player;
    }

    @Override
    protected String getCommand() {
        return "insert into KRAMLSBOT.GROUP values ("
                + "'" + group.getId() + "'" + ","
                + "'" + group.getName() + "'" + ","
                + "'" + player.getId() + "'"
                + ");";
    }
}
