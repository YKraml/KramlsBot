package domain.exceptions.messages;

import domain.exceptions.ExceptionMessage;

public class GuessingGameAlreadyExists implements ExceptionMessage {

    @Override
    public String getContent() {
        return "Ratespiel existiert schon.";
    }
}
