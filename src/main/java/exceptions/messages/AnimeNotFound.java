package exceptions.messages;

import exceptions.ExceptionMessage;

public class AnimeNotFound implements ExceptionMessage {

    private final String animeName;

    public AnimeNotFound(String animeName) {
        this.animeName = animeName;
    }

    @Override
    public String getContent() {
        return "Konnte keinen Anime mit den Namen: " + animeName + " finden.";
    }
}
