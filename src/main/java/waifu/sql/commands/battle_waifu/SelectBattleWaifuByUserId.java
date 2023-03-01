package waifu.sql.commands.battle_waifu;

import waifu.model.Player;
import waifu.sql.commands.SQLCommandWithResult;
import waifu.sql.entry.BattleWaifuEntrySet;

public class SelectBattleWaifuByUserId extends SQLCommandWithResult<BattleWaifuEntrySet> {

    private final Player player;

    public SelectBattleWaifuByUserId(Player player) {
        super(new BattleWaifuEntrySet());
        this.player = player;
    }

    @Override
    protected String getCommand() {
        return "select * from KRAMLSBOT.BATTLEWAIFU where userId like " + "'" + player.getId() + "'"
            + ";";
    }
}
