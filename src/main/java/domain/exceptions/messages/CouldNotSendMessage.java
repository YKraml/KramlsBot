package domain.exceptions.messages;

import domain.exceptions.ExceptionMessage;
import ui.messages.MyMessage;

public class CouldNotSendMessage implements ExceptionMessage {

  private final MyMessage myMessage;
  public CouldNotSendMessage(MyMessage myMessage) {
    this.myMessage = myMessage;
  }

  @Override
  public String getContent() {
    return "Could not send Message. Type = " + myMessage.getClass().getSimpleName();
  }
}
