package exceptions.messages;

import exceptions.ExceptionMessage;

public class LastSongNonExisting implements ExceptionMessage {
    @Override
    public String getContent() {
        return "Es gibt keinen letzten Song.";
    }
}
