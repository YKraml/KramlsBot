package domain.exceptions.messages;

import domain.waifu.Player;
import domain.exceptions.ExceptionMessage;

public class CouldNotSavePlayer implements ExceptionMessage {

  private final Player player;
  public CouldNotSavePlayer(Player player) {
    this.player = player;
  }

  @Override
  public String getContent() {
    return "Could not save player. Name = " + player.getName();
  }
}
