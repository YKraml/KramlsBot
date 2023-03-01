package exceptions.messages;

import exceptions.ExceptionMessage;

public class CouldNotGetUser implements ExceptionMessage {

  @Override
  public String getContent() {
    return "Konnte den Nutzer nicht finden.";
  }
}
