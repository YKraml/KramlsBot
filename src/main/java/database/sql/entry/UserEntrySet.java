package database.sql.entry;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserEntrySet extends AbstractEntrySet<UserEntrySet.UserEntry> {

    @Override
    public void addSingleResult(ResultSet resultSet) throws SQLException {
        String id = resultSet.getString(1);
        String name = resultSet.getString(2);
        int time = resultSet.getInt(3);
        int money = resultSet.getInt(4);
        int rightGuesses = resultSet.getInt(5);
        String lastDaily = resultSet.getString(6);
        int stardust = resultSet.getInt(7);
        int cookies = resultSet.getInt(8);
        int maxWaifus = resultSet.getInt(9);
        int morphStones = resultSet.getInt(10);
        UserEntry userEntry = new UserEntry(id, name, time, money, rightGuesses, lastDaily, stardust,
                cookies, maxWaifus,
                morphStones);
        this.addEntry(userEntry);
    }

    public static class UserEntry extends AbstractEntry {

        private final String id;
        private final String name;
        private final int time;
        private final int money;
        private final int rightGuesses;
        private final String lastDaily;
        private final int stardust;
        private final int cookies;
        private final int maxWaifus;
        private final int morphStones;

        public UserEntry(String id, String name, int time, int money, int rightGuesses,
                         String lastDaily, int stardust, int cookies, int maxWaifus,
                         int morphStones) {
            this.id = id;
            this.name = name;
            this.time = time;
            this.money = money;
            this.rightGuesses = rightGuesses;
            this.lastDaily = lastDaily;
            this.stardust = stardust;
            this.cookies = cookies;
            this.maxWaifus = maxWaifus;
            this.morphStones = morphStones;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getTime() {
            return time;
        }

        public int getMoney() {
            return money;
        }

        public int getRightGuesses() {
            return rightGuesses;
        }

        public String getLastDaily() {
            return lastDaily;
        }

        public int getStardust() {
            return stardust;
        }

        public int getCookies() {
            return cookies;
        }

        public int getMaxWaifus() {
            return maxWaifus;
        }

        public int getMorphStones() {
            return morphStones;
        }
    }
}
