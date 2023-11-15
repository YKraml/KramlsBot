package database.sql.mapper;

import database.sql.entry.AbstractEntrySet;
import domain.queue.QueueElement;
import database.sql.entry.LikedSongEntrySet;

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
