package waifu.sql.commands.songs;

import music.audio.QueueElement;
import waifu.sql.commands.SQLCommandWithoutResult;

public class InsertLikedSong extends SQLCommandWithoutResult {

    private final String userId;
    private final QueueElement queueElement;

    public InsertLikedSong(String userId, QueueElement queueElement) {
        this.userId = userId;
        this.queueElement = queueElement;
    }

    @Override
    protected String getCommand() {
        return "insert into KRAMLSBOT.SONG values ("
                + "'" + queueElement.getName().replace("'", "") + "'" + ","
                + "'" + queueElement.getUrl() + "'" + ","
                + "'" + queueElement.getImageUrl() + "'" + ","
                + "'" + userId + "'"
                + ");";
    }
}
