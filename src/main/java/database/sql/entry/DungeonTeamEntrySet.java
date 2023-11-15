package database.sql.entry;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DungeonTeamEntrySet extends AbstractEntrySet<DungeonTeamEntrySet.DungeonTeamEntry> {
    @Override
    public void addSingleResult(ResultSet resultSet) throws SQLException {

        String idDungeon = resultSet.getString(1);
        String idTeam = resultSet.getString(2);
        int level = resultSet.getInt(3);
        this.addEntry(new DungeonTeamEntry(idDungeon, idTeam, level));

    }

    public static class DungeonTeamEntry extends AbstractEntry {

        private final String idDungeon;
        private final String idTeam;
        private final int level;

        public DungeonTeamEntry(String idDungeon, String idTeam, int level) {
            this.idDungeon = idDungeon;
            this.idTeam = idTeam;
            this.level = level;
        }

        public String getIdDungeon() {
            return idDungeon;
        }

        public String getIdTeam() {
            return idTeam;
        }

        public int getLevel() {
            return level;
        }
    }
}
