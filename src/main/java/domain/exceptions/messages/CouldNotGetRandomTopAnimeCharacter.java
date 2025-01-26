package domain.exceptions.messages;

import domain.exceptions.ExceptionMessage;

public class CouldNotGetRandomTopAnimeCharacter implements ExceptionMessage {

  @Override
  public String getContent() {
    return "Konnte keinen zufaelligen Animecharacter finden";
  }
}
