package exceptions.messages;

import exceptions.ExceptionMessage;

public class PlaylistNotFound implements ExceptionMessage {

    private final String playlistUrl;

    public PlaylistNotFound(String playlistURL) {
        this.playlistUrl = playlistURL;

    }

    @Override
    public String getContent() {
        return "Konnte die Playlist \"" + playlistUrl + "\" nicht finden.";
    }
}
