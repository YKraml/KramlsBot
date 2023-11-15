package database.sql.entry;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RecordEntrySet extends AbstractEntrySet<RecordEntrySet.RecordEntry> {
    @Override
    public void addSingleResult(ResultSet resultSet) throws SQLException {
        String teamId = resultSet.getString(1);
        String dungeonId = resultSet.getString(2);
        String recordType = resultSet.getString(3);
        int value = resultSet.getInt(4);
        this.addEntry(new RecordEntry(teamId, dungeonId, recordType, value));
    }

    public static class RecordEntry extends AbstractEntry {

        private final String teamId;
        private final String dungeonId;
        private final String recordType;
        private final int value;

        public RecordEntry(String teamId, String dungeonId, String recordType, int value) {
            this.teamId = teamId;
            this.dungeonId = dungeonId;
            this.recordType = recordType;
            this.value = value;
        }

        public String getTeamId() {
            return teamId;
        }

        public String getDungeonId() {
            return dungeonId;
        }

        public String getRecordType() {
            return recordType;
        }

        public int getValue() {
            return value;
        }
    }
}
