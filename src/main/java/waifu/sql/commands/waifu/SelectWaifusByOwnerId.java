package waifu.sql.commands.waifu;

import waifu.model.Player;
import waifu.sql.commands.SQLCommandWithResult;
import waifu.sql.entry.WaifuEntrySet;

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
