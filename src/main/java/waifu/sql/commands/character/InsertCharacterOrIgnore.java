package waifu.sql.commands.character;

import waifu.model.Waifu;
import waifu.sql.commands.SQLCommandWithoutResult;

public class InsertCharacterOrIgnore extends SQLCommandWithoutResult {

    private final Waifu waifu;

    public InsertCharacterOrIgnore(Waifu waifu) {
        this.waifu = waifu;
    }

    @Override
    protected String getCommand() {

        String animeName = waifu.getAnimeName();
        if (animeName.length() > 50) {
            animeName = animeName.substring(0, 50);
        }


        return "insert ignore into KRAMLSBOT.CHARACTER values ("
                + waifu.getIdMal() + ","
                + "'" + waifu.getUrl() + "'" + ","
                + "'" + waifu.getImageUrl() + "'" + ","
                + "'" + waifu.getName() + "'" + ","
                + "'" + animeName + "'"
                + ");";
    }
}
