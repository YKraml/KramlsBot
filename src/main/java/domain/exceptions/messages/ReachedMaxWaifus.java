package domain.exceptions.messages;

import domain.exceptions.ExceptionMessage;
import domain.waifu.Player;

public class ReachedMaxWaifus implements ExceptionMessage {

  private final Player player;

  public ReachedMaxWaifus(Player player) {
    this.player = player;
  }

  @Override
  public String getContent() {
    return "%s, du hast deine maximale Anzahl an Waifus erreicht. Benutze 'expand', um die mögliche Anzahl an Waifus zu erhöhen.".formatted(
        player.getNameTag());
  }
}
