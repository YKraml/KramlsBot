package domain.exceptions.messages;

import domain.exceptions.ExceptionMessage;

public class NotAdmin implements ExceptionMessage {

  private final String name;

  public NotAdmin(String name) {
    this.name = name;
  }

  @Override
  public String getContent() {
    return "%s, du musst Admin für diesen Befehl sein.".formatted(name);
  }
}
