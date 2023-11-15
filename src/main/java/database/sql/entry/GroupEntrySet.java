package database.sql.entry;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupEntrySet extends AbstractEntrySet<GroupEntrySet.GroupEntry> {

    @Override
    public void addSingleResult(ResultSet resultSet) throws SQLException {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String owner = resultSet.getString(3);

            GroupEntry groupEntry = new GroupEntry(id, name, owner);
            this.addEntry(groupEntry);


    }

    public static class GroupEntry extends AbstractEntry {

        private final String id;
        private final String name;
        private final String owner;

        public GroupEntry(String id, String name, String owner) {
            this.id = id;
            this.name = name;
            this.owner = owner;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getOwner() {
            return owner;
        }
    }
}
