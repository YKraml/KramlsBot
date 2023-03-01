package waifu.sql.entry;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BattleWaifuEntrySet extends AbstractEntrySet<BattleWaifuEntrySet.BattleWaifuEntry> {
    @Override
    public void addSingleResult(ResultSet resultSet) throws SQLException {
        String userId = resultSet.getString(1);
        String waifuId = resultSet.getString(2);
        this.addEntry(new BattleWaifuEntry(userId, waifuId));

    }


    public static class BattleWaifuEntry extends AbstractEntry {

        private final String userId;
        private final String waifuId;

        public BattleWaifuEntry(String userId, String waifuId) {
            this.userId = userId;
            this.waifuId = waifuId;
        }

        public String getUserId() {
            return userId;
        }

        public String getWaifuId() {
            return waifuId;
        }
    }
}
