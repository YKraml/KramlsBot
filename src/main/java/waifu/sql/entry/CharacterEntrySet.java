package waifu.sql.entry;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CharacterEntrySet extends AbstractEntrySet<CharacterEntrySet.CharacterEntry> {
    @Override
    public void addSingleResult(ResultSet resultSet) throws SQLException {

        int idMal = resultSet.getInt(1);
        String url = resultSet.getString(2);
        String imageUrl = resultSet.getString(3);
        String name = resultSet.getString(4);
        String role = resultSet.getString(5);

        CharacterEntry characterEntry = new CharacterEntry(idMal, url, imageUrl, name, role);
        this.addEntry(characterEntry);

    }

    public static class CharacterEntry extends AbstractEntry {

        private final int idMal;
        private final String url;
        private final String imageUrl;
        private final String name;
        private final String animeName;

        public CharacterEntry(int idMal, String url, String imageUrl, String name, String animeName) {
            this.idMal = idMal;
            this.url = url;
            this.imageUrl = imageUrl;
            this.name = name;
            this.animeName = animeName;
        }

        public int getIdMal() {
            return idMal;
        }

        public String getUrl() {
            return url;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getName() {
            return name;
        }

        public String getAnimeName() {
            return animeName;
        }
    }
}
