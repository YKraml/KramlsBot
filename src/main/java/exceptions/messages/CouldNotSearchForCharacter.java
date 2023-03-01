package exceptions.messages;

import exceptions.ExceptionMessage;

public class CouldNotSearchForCharacter implements ExceptionMessage {

    private final String characterName;

    public CouldNotSearchForCharacter(String guessedName) {
        super();
        this.characterName = guessedName;
    }

    @Override
    public String getContent() {
        return "Konnte nicht nach dem Namen \"" + characterName + "\" suchen.";
    }
}
