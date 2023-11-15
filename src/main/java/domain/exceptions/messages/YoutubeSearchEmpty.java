package domain.exceptions.messages;

import domain.exceptions.ExceptionMessage;

public class YoutubeSearchEmpty implements ExceptionMessage {

    private final String songName;

    public YoutubeSearchEmpty(String songName) {
        this.songName = songName;
    }

    @Override
    public String getContent() {
        return "Konnte den Song \"" + songName + "\" nicht finden, da die Youtube-Suche erfolglos war.";
    }
}
