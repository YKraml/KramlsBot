package exceptions.messages;

import exceptions.ExceptionMessage;

public class NextSongNonExisting implements ExceptionMessage {
    @Override
    public String getContent() {
        return "Es gibt keinen naechsten Song.";
    }
}
