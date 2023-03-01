package waifu.sql.commands.battle_waifu;

import waifu.sql.commands.SQLCommandWithoutResult;
import waifu.model.Player;
import waifu.model.Waifu;

public class AlterBattleWaifu extends SQLCommandWithoutResult {

    private final Waifu waifu;
    private final Player player;

    public AlterBattleWaifu(Waifu waifu, Player player) {
        this.waifu = waifu;
        this.player = player;
    }

    @Override
    protected String getCommand() {
        return "update KRAMLSBOT.BATTLEWAIFU set "
                + "waifuId = " + "'" + waifu.getId() + "'"
                + "where userId like " + "'" + player.getId() + "'"
                + ";";
    }
}
