package exceptions.messages;

import exceptions.ExceptionMessage;

public class CouldNotFindVoiceChannel implements ExceptionMessage {

    private final String serverName;
    private final String userName;

    public CouldNotFindVoiceChannel(String serverName, String userName) {
        this.serverName = serverName;
        this.userName = userName;
    }

    @Override
    public String getContent() {
        return "Konnte den Voicechannel auf dem Server \"" + serverName + "\" mit dem Nutzer \"" + userName + "\" nicht finden.";
    }
}
