package database.sql.commands.waifu;

import database.sql.commands.SQLCommandWithoutResult;
import domain.waifu.Waifu;

public class DeleteWaifu extends SQLCommandWithoutResult {

  private final Waifu waifu;

  public DeleteWaifu(Waifu waifu) {
    this.waifu = waifu;
  }

  @Override
  protected String getCommand() {
    return "delete from KRAMLSBOT.WAIFU where KRAMLSBOT.WAIFU.id like " + "'"
        + waifu.getId() + "'" + ";";
  }
}
