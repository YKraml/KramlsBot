package exceptions.messages;

import exceptions.ExceptionMessage;

public class CouldNotStartRoutine implements ExceptionMessage {
    @Override
    public String getContent() {
        return "Beim ausführen der Routine der Nachricht kam es zu einer Exception";
    }
}
