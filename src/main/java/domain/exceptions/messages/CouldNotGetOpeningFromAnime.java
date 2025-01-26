package domain.exceptions.messages;

import domain.exceptions.ExceptionMessage;

public class CouldNotGetOpeningFromAnime implements ExceptionMessage {

  private final String title;

  public CouldNotGetOpeningFromAnime(String title) {
    this.title = title;

  }

  @Override
  public String getContent() {
    return "Konnte kein Opening vom Anime \"" + title + "\" finden.";
  }
}
