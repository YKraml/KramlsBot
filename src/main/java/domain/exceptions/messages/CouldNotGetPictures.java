package domain.exceptions.messages;

import domain.exceptions.ExceptionMessage;

public class CouldNotGetPictures implements ExceptionMessage {

    private final String characterName;

    public CouldNotGetPictures(String name) {
        characterName = name;
    }

    @Override
    public String getContent() {
        return "Konnte keine Bilder von '%s' finden.".formatted(characterName);
    }
}
