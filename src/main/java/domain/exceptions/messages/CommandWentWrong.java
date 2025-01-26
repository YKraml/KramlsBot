package domain.exceptions.messages;

import domain.exceptions.ExceptionMessage;

public class CommandWentWrong implements ExceptionMessage {

  private final String errorMessage;

  public CommandWentWrong(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  @Override
  public String getContent() {
    return errorMessage;
  }
}
