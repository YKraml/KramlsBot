package domain.exceptions.messages;

import domain.exceptions.ExceptionMessage;

public class CouldNotGetChannel implements ExceptionMessage {

    @Override
    public String getContent() {
        return "Konnte den Channel nicht finden.";
    }
}
