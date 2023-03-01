package waifu.sql.commands.group;

import waifu.model.Player;
import waifu.sql.commands.SQLCommandWithResult;
import waifu.sql.entry.GroupEntrySet;

public class SelectGroupsByOwnerId extends SQLCommandWithResult<GroupEntrySet> {

    private final Player player;

    public SelectGroupsByOwnerId(Player player) {
        super(new GroupEntrySet());
        this.player = player;
    }


    @Override
    protected String getCommand() {
        return "select * from KRAMLSBOT.GROUP where owner like " + "'" + player.getId() + "'" + ";";
    }
}
