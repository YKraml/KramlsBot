package exceptions.messages;

import exceptions.ExceptionMessage;

public class GuessingGameAlreadyExists implements ExceptionMessage {

  @Override
  public String getContent() {
    return "Ratespiel existiert schon.";
  }
}
