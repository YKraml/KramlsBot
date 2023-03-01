package waifu.sql.commands.battle_waifu;

import waifu.sql.commands.SQLCommandWithoutResult;
import waifu.model.Waifu;

public class DeleteBattleWaifu extends SQLCommandWithoutResult {

    private final Waifu waifu;

    public DeleteBattleWaifu(Waifu waifu) {
        this.waifu = waifu;
    }

    @Override
    protected String getCommand() {
        return "delete from KRAMLSBOT.BATTLEWAIFU where waifuId like " + "'" + waifu.getId() + "'" + ";";
    }
}
