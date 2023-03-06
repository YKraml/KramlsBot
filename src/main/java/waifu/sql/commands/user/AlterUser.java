package waifu.sql.commands.user;

import waifu.sql.commands.SQLCommandWithoutResult;
import waifu.model.Player;

public class AlterUser extends SQLCommandWithoutResult {

  private final Player player;

  public AlterUser(Player player) {
    this.player = player;
  }

  @Override
  protected String getCommand() {
    return "update KRAMLSBOT.USER set "
        + "name = " + "'" + player.getName() + "'" + ","
        + "money = " + player.getInventory().getMoney() + ","
        + "rightGuesses = " + player.getRightGuesses() + ","
        + "lastDaily = " + "'" + player.getLastDaily() + "'" + ","
        + "stardust = " + player.getInventory().getStardust() + ","
        + "bonbons = " + player.getInventory().getCookies() + ","
        + "maxWaifus = " + player.getMaxWaifus() + ","
        + "morphStones = " + player.getInventory().getMorphStones()
        + " where id like " + player.getId();

  }
}
