package waifu.sql.commands.songs;

import waifu.sql.commands.SQLCommandWithResult;
import waifu.sql.entry.LikedSongEntrySet;

public class SelectLikedSongs extends SQLCommandWithResult<LikedSongEntrySet> {

    private final String userId;

    public SelectLikedSongs(String userId) {
        super(new LikedSongEntrySet());
        this.userId = userId;
    }

    @Override
    protected String getCommand() {
        return "select * from KRAMLSBOT.SONG where userID like " + "'" + userId + "'" + ";";
    }
}
