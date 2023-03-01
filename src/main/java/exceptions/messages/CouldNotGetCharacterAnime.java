package exceptions.messages;

import exceptions.ExceptionMessage;

public class CouldNotGetCharacterAnime implements ExceptionMessage {

    private final String characterName;

    public CouldNotGetCharacterAnime(String name) {
        super();
        this.characterName = name;
    }

    @Override
    public String getContent() {
        return "Konnte nicht die Animeinformationen des Characters \"" + characterName + "\" finden.";
    }
}
