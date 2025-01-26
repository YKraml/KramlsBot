package domain.exceptions.messages;

import domain.exceptions.ExceptionMessage;

public class FighterNotFound implements ExceptionMessage {

  private final int listPosition;

  public FighterNotFound(int listPosition) {
    this.listPosition = listPosition;
  }

  @Override
  public String getContent() {
    return "Konnte die Waifu an der Stelle %d nicht finden.".formatted(listPosition);
  }
}
