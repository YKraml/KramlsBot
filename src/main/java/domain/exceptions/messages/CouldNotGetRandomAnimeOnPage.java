package domain.exceptions.messages;

import domain.exceptions.ExceptionMessage;

public class CouldNotGetRandomAnimeOnPage implements ExceptionMessage {

  private final int page;

  public CouldNotGetRandomAnimeOnPage(int page) {
    this.page = page;
  }

  @Override
  public String getContent() {
    return "Konnte keinen Anime auf der Seite " + page + " finden.";
  }
}
