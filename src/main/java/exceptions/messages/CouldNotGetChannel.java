package exceptions.messages;

import exceptions.ExceptionMessage;

public class CouldNotGetChannel implements ExceptionMessage {

  @Override
  public String getContent() {
    return "Konnte den Channel nicht finden.";
  }
}
