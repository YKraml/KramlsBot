package exceptions.messages;

import exceptions.ExceptionMessage;
import waifu.model.Player;

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
