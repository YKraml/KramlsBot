package domain.exceptions.messages;

import domain.exceptions.ExceptionMessage;
import domain.waifu.Player;

public class TeamNotFound implements ExceptionMessage {

  private final Player player;
  private final String teamName;

  public TeamNotFound(Player player, String teamName) {
    this.player = player;
    this.teamName = teamName;
  }

  @Override
  public String getContent() {
    return player.getNameTag() + ", konnte Team \"" + teamName + "\" nicht finden.";
  }
}
