package database.sql.commands.group;

import database.sql.commands.SQLCommandWithoutResult;
import domain.waifu.Group;
import domain.waifu.Player;

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
