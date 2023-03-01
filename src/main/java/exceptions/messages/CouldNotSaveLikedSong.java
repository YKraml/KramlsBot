package exceptions.messages;

import exceptions.ExceptionMessage;

public class CouldNotSaveLikedSong implements ExceptionMessage {

    private final String userId;
    private final String songName;


    public CouldNotSaveLikedSong(String userId, String songName) {
        this.userId = userId;
        this.songName = songName;
    }

    @Override
    public String getContent() {
        return "Konnte den Song \"" + songName + "\" von \"" + userId + "\" nicht speichern";
    }
}
