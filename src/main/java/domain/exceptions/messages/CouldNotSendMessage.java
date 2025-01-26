package domain.exceptions.messages;

import domain.exceptions.ExceptionMessage;

public class CouldNotSendMessage implements ExceptionMessage {

  private final String messageName;

  public CouldNotSendMessage(String messageName) {
    this.messageName = messageName;
  }

  @Override
  public String getContent() {
    return "Could not send Message. Type = " + messageName;
  }
}
