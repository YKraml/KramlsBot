package exceptions.messages;

import exceptions.ExceptionMessage;

public class CouldNotSpawnWaifu implements ExceptionMessage {
    public String getContent() {
        return "Konnte keine neue Waifu spawnen.";
    }
}
