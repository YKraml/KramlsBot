package waifu.sql.commands.waifu;

import waifu.model.Player;
import waifu.model.Waifu;
import waifu.sql.commands.SQLCommandWithoutResult;

public class InsertWaifuOrUpdate extends SQLCommandWithoutResult {

    private final Player player;
    private final Waifu waifu;

    public InsertWaifuOrUpdate(Player player, Waifu waifu) {
        this.player = player;
        this.waifu = waifu;
    }

    @Override
    protected String getCommand() {
        return "insert into KRAMLSBOT.WAIFU values ('%s',%s,'%s',%d,%d,%d,%d,%d,%d,'%s','%s',%d) on duplicate key update rarity = '%s',level = %d,xp = %d,baseHp = %d,baseAtt = %d,baseDef = %d,baseInit = %d,owner = '%s',imageUrl = '%s',starLevel = %d;".formatted(
            waifu.getId(), waifu.getIdMal(), waifu.getRarity(), waifu.getLevel(), waifu.getXp(),
            waifu.getBaseHp(), waifu.getBaseAtt(), waifu.getBaseDef(), waifu.getBaseInit(),
            player.getId(), waifu.getImageUrl(), waifu.getStarLevel(), waifu.getRarity(),
            waifu.getLevel(), waifu.getXp(), waifu.getBaseHp(), waifu.getBaseAtt(),
            waifu.getBaseDef(), waifu.getBaseInit(), player.getId(), waifu.getImageUrl(),
            waifu.getStarLevel());
    }
}
