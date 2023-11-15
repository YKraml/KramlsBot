package database.sql.entry;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WaifuEntrySet extends AbstractEntrySet<WaifuEntrySet.WaifuEntry> {

    @Override
    public void addSingleResult(ResultSet resultSet) throws SQLException {
        String id = resultSet.getString(1);
        int idMal = resultSet.getInt(2);
        String rarity = resultSet.getString(3);
        int level = resultSet.getInt(4);
        int xp = resultSet.getInt(5);
        int baseHp = resultSet.getInt(6);
        int baseAtt = resultSet.getInt(7);
        int baseDef = resultSet.getInt(8);
        int baseInit = resultSet.getInt(9);
        String owner = resultSet.getString(10);
        String imageUrl = resultSet.getString(11);
        int starLevel = resultSet.getInt(12);
        WaifuEntry waifuEntry = new WaifuEntry(id, idMal, rarity, level, xp, baseHp, baseAtt, baseDef, baseInit, owner, imageUrl, starLevel);
        this.addEntry(waifuEntry);
    }


    public static class WaifuEntry extends AbstractEntry {

        private final String id;
        private final int idMal;
        private final String rarity;
        private final int level;
        private final int starLevel;
        private final int xp;
        private final int baseHp;
        private final int baseAtt;
        private final int baseDef;
        private final int baseInit;
        private final String owner;
        private final String imageUrl;

        public WaifuEntry(String id, int idMal, String rarity, int level, int xp, int baseHp, int baseAtt, int baseDef, int baseInit, String owner, String imageUrl, int starLevel) {
            this.id = id;
            this.idMal = idMal;
            this.rarity = rarity;
            this.level = level;
            this.starLevel = starLevel;
            this.xp = xp;
            this.baseHp = baseHp;
            this.baseAtt = baseAtt;
            this.baseDef = baseDef;
            this.baseInit = baseInit;
            this.owner = owner;
            this.imageUrl = imageUrl;
        }

        public String getId() {
            return id;
        }

        public int getIdMal() {
            return idMal;
        }

        public String getRarity() {
            return rarity;
        }

        public int getLevel() {
            return level;
        }

        public int getXp() {
            return xp;
        }

        public int getBaseHp() {
            return baseHp;
        }

        public int getBaseAtt() {
            return baseAtt;
        }

        public int getBaseDef() {
            return baseDef;
        }

        public int getBaseInit() {
            return baseInit;
        }

        public String getOwner() {
            return owner;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public int getStarLevel() {
            return starLevel;
        }
    }
}
