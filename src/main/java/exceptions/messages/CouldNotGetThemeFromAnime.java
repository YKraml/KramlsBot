package exceptions.messages;

import exceptions.ExceptionMessage;

public class CouldNotGetThemeFromAnime implements ExceptionMessage {

  private final String title;

  public CouldNotGetThemeFromAnime(String title) {
    this.title = title;
  }

  @Override
  public String getContent() {
    return "Konnte nicht die Themes von \"" + title + "\" finden.";
  }
}
