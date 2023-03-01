package exceptions.messages;

import exceptions.ExceptionMessage;

public class CouldNotGetGuessingGameByServer implements ExceptionMessage {

    private final String serverId;

    public CouldNotGetGuessingGameByServer(String serverId) {
        this.serverId = serverId;
    }

    @Override
    public String getContent() {
        return "Konnte kein Ratespiel vom Server \"" + serverId + " finden.";
    }
}
