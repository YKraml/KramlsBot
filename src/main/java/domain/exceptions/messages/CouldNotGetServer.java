package domain.exceptions.messages;

import domain.exceptions.ExceptionMessage;

public class CouldNotGetServer implements ExceptionMessage {

  @Override
  public String getContent() {
    return "Konne den Server nicht finden.";
  }
}
