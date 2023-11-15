package database.sql.commands.waifu;

import database.sql.commands.SQLCommandWithResult;
import database.sql.entry.WaifuEntrySet;
import domain.waifu.Player;

public class SelectWaifusByOwnerId extends SQLCommandWithResult<WaifuEntrySet> {

    private final Player player;

    public SelectWaifusByOwnerId(Player player) {
        super(new WaifuEntrySet());
        this.player = player;
    }

    @Override
    protected String getCommand() {
        return "select * from KRAMLSBOT.WAIFU " +
                "where owner like " + "'" + player.getId() + "'" + ";";
    }
}
