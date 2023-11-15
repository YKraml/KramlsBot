package domain.exceptions.messages;

import domain.exceptions.ExceptionMessage;

public class NotEnoughResource implements ExceptionMessage {

  private final long actual;
  private final long needed;
  private final String resource;

  public NotEnoughResource(long actual, long needed, String resource) {
    this.actual = actual;
    this.needed = needed;
    this.resource = resource;
  }


  @Override
  public String getContent() {
    return "Du hast nicht genug %s. Du hast nur %d, brauchst aber %d.".formatted(resource,
        actual, needed);

  }
}
