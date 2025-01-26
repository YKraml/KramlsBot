package domain.exceptions.messages;

import domain.exceptions.ExceptionMessage;

public class TimeOut implements ExceptionMessage {

  private final String url;

  public TimeOut(String urlString) {
    this.url = urlString;
  }

  @Override
  public String getContent() {
    return "Bei der Verbindung kam es zu einem Time-out. URL: \"" + url
        + "\" konnte nicht abgefragt werden.";
  }
}
