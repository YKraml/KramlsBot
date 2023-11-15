package domain.exceptions.messages;

import domain.exceptions.ExceptionMessage;

public class CouldNotLoadPlayer implements ExceptionMessage {

    private final String userId;

    public CouldNotLoadPlayer(String userId) {
        this.userId = userId;
    }

    @Override
    public String getContent() {
        return "Konnte den Spieler mit der ID: " + userId + " nicht laden.";
    }
}
