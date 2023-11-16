package domain.exceptions.messages;

import domain.exceptions.ExceptionMessage;

public class CouldNotGetOriginFromCharachter implements ExceptionMessage {

    private final String characterName;

    public CouldNotGetOriginFromCharachter(String name) {
        this.characterName = name;
    }

    @Override
    public String getContent() {
        return "Konnte die Herkunft von '%s' nicht finden.".formatted(characterName);
    }
}
