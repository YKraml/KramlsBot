package database.sql.entry;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupWaifuEntrySet extends AbstractEntrySet<GroupWaifuEntrySet.GroupWaifuEntry> {
    @Override
    public void addSingleResult(ResultSet resultSet) throws SQLException {
        String idGroup = resultSet.getString(1);
        String idWaifu = resultSet.getString(2);
        this.addEntry(new GroupWaifuEntry(idGroup, idWaifu));

    }

    public static class GroupWaifuEntry extends AbstractEntry {

        private final String idGroup;
        private final String idWaifu;

        public GroupWaifuEntry(String idGroup, String idWaifu) {
            this.idGroup = idGroup;
            this.idWaifu = idWaifu;
        }

        public String getIdGroup() {
            return idGroup;
        }

        public String getIdWaifu() {
            return idWaifu;
        }
    }
}
