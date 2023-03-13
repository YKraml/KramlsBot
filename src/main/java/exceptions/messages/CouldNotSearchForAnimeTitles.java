package exceptions.messages;

import exceptions.ExceptionMessage;

public class CouldNotSearchForAnimeTitles implements ExceptionMessage {

  private final String guess;

  public CouldNotSearchForAnimeTitles(String guess) {
    this.guess = guess;
  }

  @Override
  public String getContent() {
    return "Konnte nicht die moeglichen Titel für den Tipp \"" + guess + "\" finden";
  }
}
