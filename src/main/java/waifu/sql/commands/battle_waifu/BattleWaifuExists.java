package waifu.sql.commands.battle_waifu;

import waifu.model.Player;
import waifu.sql.commands.SQLCommandCheckExistence;

public class BattleWaifuExists extends SQLCommandCheckExistence {

    protected final Player player;

    public BattleWaifuExists(Player player) {
        this.player = player;
    }

    @Override
    protected String getCommand() {
        return "select * from KRAMLSBOT.BATTLEWAIFU where userId like " + "'" + player.getId() + "'"
            + ";";
    }
}
