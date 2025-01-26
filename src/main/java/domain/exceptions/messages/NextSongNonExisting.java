package domain.exceptions.messages;

import domain.exceptions.ExceptionMessage;

public class NextSongNonExisting implements ExceptionMessage {

  @Override
  public String getContent() {
    return "Es gibt keinen naechsten Song.";
  }
}
