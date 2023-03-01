package waifu.sql.mapper;

import exceptions.MyOwnException;
import music.audio.QueueElement;
import waifu.sql.entry.LikedSongEntrySet;
import waifu.sql.entry.AbstractEntrySet;

public class LikedSongMapper extends AbstractMapper<QueueElement, LikedSongEntrySet.LikedSongEntry> {

    private final String playerName;

    public LikedSongMapper(AbstractEntrySet<LikedSongEntrySet.LikedSongEntry> entrySet, String playerName) {
        super(entrySet);
        this.playerName = playerName;
    }

    @Override
    QueueElement mapOneEntry(LikedSongEntrySet.LikedSongEntry entry) throws MyOwnException {
        return new QueueElement(entry.getName(), entry.getUrl(), playerName);
    }
}
