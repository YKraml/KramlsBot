package waifu.sql.entry;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DungeonEntrySet extends AbstractEntrySet<DungeonEntrySet.DungeonEntry> {

    @Override
    public void addSingleResult(ResultSet resultSet) throws SQLException {
        String channelId = resultSet.getString(1);
        String name = resultSet.getString(2);
        int difficulty = resultSet.getInt(3);
        String serverId = resultSet.getString(4);
        this.addEntry(new DungeonEntry(channelId, name, difficulty, serverId));


    }

    public static class DungeonEntry extends AbstractEntry {

        private final String channelId;
        private final String name;
        private final int difficulty;
        private final String serverId;


        public DungeonEntry(String channelId, String name, int difficulty, String serverId) {
            this.channelId = channelId;
            this.name = name;
            this.difficulty = difficulty;
            this.serverId = serverId;
        }

        public String getChannelId() {
            return channelId;
        }

        public String getName() {
            return name;
        }

        public int getDifficulty() {
            return difficulty;
        }

        public String getServerId() {
            return serverId;
        }
    }
}
