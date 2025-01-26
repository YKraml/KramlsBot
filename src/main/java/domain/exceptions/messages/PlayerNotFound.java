package domain.exceptions.messages;

import domain.exceptions.ExceptionMessage;

public class PlayerNotFound implements ExceptionMessage {

  private final String nameOrTag;

  public PlayerNotFound(String nameOrTag) {
    this.nameOrTag = nameOrTag;
  }

  @Override
  public String getContent() {
    return "Konnte den Spieler " + nameOrTag + " nicht finden.";
  }
}
