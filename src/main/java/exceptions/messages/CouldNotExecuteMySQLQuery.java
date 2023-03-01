package exceptions.messages;

import exceptions.ExceptionMessage;

public class CouldNotExecuteMySQLQuery implements ExceptionMessage {

    private final String command;

    public CouldNotExecuteMySQLQuery(String command) {
        this.command = command;
    }

    @Override
    public String getContent() {
        return "Konnte den SQL Befehl '%s' nicht ausfueren.".formatted(this.command);
    }
}
