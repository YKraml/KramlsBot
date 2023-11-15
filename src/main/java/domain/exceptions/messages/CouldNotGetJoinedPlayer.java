package domain.exceptions.messages;

import domain.exceptions.ExceptionMessage;

public class CouldNotGetJoinedPlayer implements ExceptionMessage {

    private final String id;

    public CouldNotGetJoinedPlayer(String id) {
        this.id = id;
    }

    @Override
    public String getContent() {
        return "Konnte den joined Player " + id + " nicht finden.";
    }
}
