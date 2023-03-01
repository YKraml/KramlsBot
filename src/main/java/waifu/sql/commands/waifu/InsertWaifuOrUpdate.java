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
        return "insert into KRAMLSBOT.WAIFU values (" +
            "'" + waifu.getId() + "'" + "," +
                waifu.getIdMal() + "," +
            "'" + waifu.getRarity() + "'" + "," +
                waifu.getLevel() + "," +
                waifu.getXp() + "," +
                waifu.getBaseHp() + "," +
                waifu.getBaseAtt() + "," +
                waifu.getBaseDef() + "," +
                waifu.getBaseInit() + "," +
            "'" + player.getId() + "'" + "," +
            "'" + waifu.getImageUrl() + "'" + "," +
                waifu.getStarLevel() +
                ") on duplicate key update " +
                "rarity = " + "'" + waifu.getRarity() + "'" + "," +
                "level = " + waifu.getLevel() + "," +
                "xp = " + waifu.getXp() + "," +
                "baseHp = " + waifu.getBaseHp() + "," +
                "baseAtt = " + waifu.getBaseAtt() + "," +
                "baseDef = " + waifu.getBaseDef() + "," +
                "baseInit = " + waifu.getBaseInit() + "," +
                "owner = " + "'" + player.getId() + "'" + "," +
                "imageUrl = " + "'" + waifu.getImageUrl() + "'" + "," +
                "starLevel = " + waifu.getStarLevel()+
                ";";
    }
}
