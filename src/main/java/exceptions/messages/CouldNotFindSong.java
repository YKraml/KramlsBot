package exceptions.messages;

import exceptions.ExceptionMessage;

public class CouldNotFindSong implements ExceptionMessage {

  private final String title;

  public CouldNotFindSong(String title) {
    this.title = title;
  }

  @Override
  public String getContent() {
    return "Konnte das Lied '%s' nicht finden.".formatted(title);
  }
}
