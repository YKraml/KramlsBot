package domain.exceptions.messages;

import domain.exceptions.ExceptionMessage;

public class CouldNotGetAnimeCharactersFromAnime implements ExceptionMessage {

    private final String title;

    public CouldNotGetAnimeCharactersFromAnime(String title) {
        this.title = title;
    }

    @Override
    public String getContent() {
        return "Konnte nicht die Charactere vom Anime \"" + title + "\" finden.";
    }
}
