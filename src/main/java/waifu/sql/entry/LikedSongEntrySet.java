package waifu.sql.entry;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LikedSongEntrySet extends AbstractEntrySet<LikedSongEntrySet.LikedSongEntry> {

    @Override
    public void addSingleResult(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString(1);
        String url = resultSet.getString(2);
        String imageUrl = resultSet.getString(3);
        String userId = resultSet.getString(4);
        LikedSongEntry likedSongEntry = new LikedSongEntry(name, url, imageUrl, userId);
        this.addEntry(likedSongEntry);

    }

    public static class LikedSongEntry extends AbstractEntry {

        private final String name;
        private final String url;
        private final String imageUrl;
        private final String userId;

        public LikedSongEntry(String name, String url, String imageUrl, String userId) {
            this.name = name;
            this.url = url;
            this.imageUrl = imageUrl;
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getUserId() {
            return userId;
        }
    }
}
