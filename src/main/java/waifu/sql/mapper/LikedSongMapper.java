package waifu.sql.mapper;

import music.queue.QueueElement;
import waifu.sql.entry.AbstractEntrySet;
import waifu.sql.entry.LikedSongEntrySet;

public class LikedSongMapper extends AbstractMapper<QueueElement, LikedSongEntrySet.LikedSongEntry> {

    private final String playerName;

    public LikedSongMapper(AbstractEntrySet<LikedSongEntrySet.LikedSongEntry> entrySet, String playerName) {
        super(entrySet);
        this.playerName = playerName;
    }

    @Override
    QueueElement mapOneEntry(LikedSongEntrySet.LikedSongEntry entry) {
        return new QueueElement(entry.getName(), entry.getUrl(), playerName);
    }
}
