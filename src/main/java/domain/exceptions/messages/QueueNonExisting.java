package domain.exceptions.messages;

import domain.exceptions.ExceptionMessage;
import org.javacord.api.entity.server.Server;

public class QueueNonExisting implements ExceptionMessage {

    private final Server server;

    public QueueNonExisting(Server server) {
        this.server = server;
    }

    @Override
    public String getContent() {
        return "Konnte die Queue fuer den Server \"" + server.getName() + "\" nicht finden.";
    }
}
