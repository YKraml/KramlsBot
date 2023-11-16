package domain.exceptions.messages;

import domain.exceptions.ExceptionMessage;

public class CouldNotGetUser implements ExceptionMessage {

    @Override
    public String getContent() {
        return "Konnte den Nutzer nicht finden.";
    }
}
