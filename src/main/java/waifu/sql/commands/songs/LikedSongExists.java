package waifu.sql.commands.songs;

import waifu.sql.commands.SQLCommandCheckExistence;

public class LikedSongExists extends SQLCommandCheckExistence {

    private final String userId;
    private final String url;

    public LikedSongExists(String userId, String url) {
        this.userId = userId;
        this.url = url;
    }

    @Override
    protected String getCommand() {
        return "select * from KRAMLSBOT.SONG where userId like " + "'" + userId + "'" + " and url like " + "'"
            + url + "'" + ";";
    }
}
