package exceptions.messages;

import exceptions.ExceptionMessage;

public class UserNotFound implements ExceptionMessage {
    private final String name;

    public UserNotFound(String name) {
        this.name = name;
    }

    @Override
    public String getContent() {
        return "Konnte den Nutzer " + name + " nicht finden.";
    }
}
