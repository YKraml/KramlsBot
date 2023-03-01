package exceptions.messages;

import exceptions.ExceptionMessage;

public class CouldNotGetServer implements ExceptionMessage {

  @Override
  public String getContent() {
    return "Konne den Server nicht finden.";
  }
}
