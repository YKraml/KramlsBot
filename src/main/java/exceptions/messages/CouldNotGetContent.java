package exceptions.messages;

import exceptions.ExceptionMessage;

public class CouldNotGetContent implements ExceptionMessage {
    @Override
    public String getContent() {
        return "Konnte den Inhalt der Nachricht nicht generieren.";
    }
}
