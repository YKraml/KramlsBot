package database.sql.commands.group_waifu;

import database.sql.commands.SQLCommandWithoutResult;
import domain.waifu.Group;
import domain.waifu.Waifu;

public class InsertGroupWaifu extends SQLCommandWithoutResult {

  private final Group group;
  private final Waifu waifu;

  public InsertGroupWaifu(Group group, Waifu waifu) {
    this.group = group;
    this.waifu = waifu;
  }

  @Override
  protected String getCommand() {
    return "insert into KRAMLSBOT.GROUP_WAIFU values ("
        + "'" + group.getId() + "'" + ","
        + "'" + waifu.getId() + "'" +
        ");";
  }
}
