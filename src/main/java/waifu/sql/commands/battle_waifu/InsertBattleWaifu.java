package waifu.sql.commands.battle_waifu;

import waifu.sql.commands.SQLCommandWithoutResult;
import waifu.model.Player;
import waifu.model.Waifu;

public class InsertBattleWaifu extends SQLCommandWithoutResult {

    private final Waifu waifu;
    private final Player player;

    public InsertBattleWaifu(Waifu waifu, Player player) {
        this.waifu = waifu;
        this.player = player;
    }

    @Override
    protected String getCommand() {
        return "insert into KRAMLSBOT.BATTLEWAIFU values ("
                + "'" + player.getId() + "'" + ","
                + "'" + waifu.getId() + "'"
                + ");";
    }
}
