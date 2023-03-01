package waifu.sql.entry;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamEntrySet extends AbstractEntrySet<TeamEntrySet.TeamEntry> {
    @Override
    public void addSingleResult(ResultSet resultSet) throws SQLException {
        String id = resultSet.getString(1);
        String name = resultSet.getString(2);
        String playerId = resultSet.getString(3);
        int teamsize = resultSet.getInt(4);
        boolean isInDungeon = resultSet.getBoolean(5);
        int money = resultSet.getInt(6);
        int stardust = resultSet.getInt(7);
        int cookies = resultSet.getInt(8);
        String dungeon = resultSet.getString(9);
        int level = resultSet.getInt(10);

        TeamEntry teamEntry = new TeamEntry(id, name, playerId, teamsize, isInDungeon, money, stardust, cookies, dungeon, level);
        this.addEntry(teamEntry);

    }

    public static class TeamEntry extends AbstractEntry {

        private final String id;
        private final String name;
        private final String playerId;
        private final int teamsize;
        private final boolean isInDungeon;
        private final int money;
        private final int stardust;
        private final int cookies;
        private final String dungeon;
        private final int level;

        public TeamEntry(String id, String name, String playerId, int teamsize, boolean isInDungeon, int money, int stardust, int cookies, String dungeon, int level) {
            this.id = id;
            this.name = name;
            this.playerId = playerId;
            this.teamsize = teamsize;
            this.isInDungeon = isInDungeon;
            this.money = money;
            this.stardust = stardust;
            this.cookies = cookies;
            this.dungeon = dungeon;
            this.level = level;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getPlayerId() {
            return playerId;
        }

        public int getTeamsize() {
            return teamsize;
        }

        public boolean isInDungeon() {
            return isInDungeon;
        }

        public int getMoney() {
            return money;
        }

        public int getStardust() {
            return stardust;
        }

        public int getCookies() {
            return cookies;
        }

        public String getDungeon() {
            return dungeon;
        }

        public int getLevel() {
            return level;
        }
    }
}
