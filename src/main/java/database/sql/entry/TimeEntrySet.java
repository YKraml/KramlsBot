package database.sql.entry;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TimeEntrySet extends AbstractEntrySet<TimeEntrySet.TimeEntry> {

    @Override
    public void addSingleResult(ResultSet resultSet) throws SQLException {

        String userId = resultSet.getString(1);
        String serverId = resultSet.getString(2);
        long time = resultSet.getLong(3);
        TimeEntry timeEntry = new TimeEntry(userId, serverId, time);
        this.addEntry(timeEntry);

    }

    public static class TimeEntry extends AbstractEntry {

        private final String idUser;
        private final String idServer;
        private final long time;

        public TimeEntry(String idUser, String idServer, long time) {
            this.idUser = idUser;
            this.idServer = idServer;
            this.time = time;
        }

        public String getIdServer() {
            return idServer;
        }

        public String getIdUser() {
            return idUser;
        }

        public long getTime() {
            return time;
        }
    }
}
