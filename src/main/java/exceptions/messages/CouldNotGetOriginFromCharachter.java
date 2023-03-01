package exceptions.messages;

import exceptions.ExceptionMessage;

public class CouldNotGetOriginFromCharachter implements ExceptionMessage {

    private final String characterName;

    public CouldNotGetOriginFromCharachter(String name) {
        super();
        this.characterName = name;
    }

    @Override
    public String getContent() {
        return "Konnte die Herkunft von '%s' nicht finden.".formatted(characterName);
    }
}
