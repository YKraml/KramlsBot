package exceptions.messages;

import exceptions.ExceptionMessage;

public class CouldNotGetRandomTopAnimeCharacter implements ExceptionMessage {
    @Override
    public String getContent() {
        return "Konnte keinen zufaelligen Animecharacter finden";
    }
}
