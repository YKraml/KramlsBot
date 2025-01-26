package domain.exceptions.messages;

import domain.exceptions.ExceptionMessage;

public class BattleWaifuNotFound implements ExceptionMessage {

  @Override
  public String getContent() {
    return "Konnte die BattleWaifu nicht finden";
  }
}
