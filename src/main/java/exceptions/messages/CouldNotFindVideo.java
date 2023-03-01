package exceptions.messages;

import exceptions.ExceptionMessage;

public class CouldNotFindVideo implements ExceptionMessage {

    private final String videoName;

    public CouldNotFindVideo(String videoName) {
        this.videoName = videoName;
    }

    @Override
    public String getContent() {
        return "Konnte kein Video mit dem Namen \"" + videoName + "\" finden.";
    }
}
