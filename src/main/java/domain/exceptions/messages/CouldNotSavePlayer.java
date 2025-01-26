package domain.exceptions.messages;

import domain.exceptions.ExceptionMessage;
import domain.waifu.Player;

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
