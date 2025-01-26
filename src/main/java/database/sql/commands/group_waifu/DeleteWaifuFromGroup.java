package database.sql.commands.group_waifu;

import database.sql.commands.SQLCommandWithoutResult;
import domain.waifu.Group;
import domain.waifu.Waifu;

public class DeleteWaifuFromGroup extends SQLCommandWithoutResult {

  private final Group group;
  private final Waifu waifu;

  public DeleteWaifuFromGroup(Group group, Waifu waifu) {
    this.group = group;
    this.waifu = waifu;
  }

  @Override
  protected String getCommand() {
    return "delete from KRAMLSBOT.GROUP_WAIFU where "
        + "idWaifu = " + "'" + waifu.getId() + "'"
        + " and "
        + "idGroup = " + "'" + group.getId() + "'"
        + ";";
  }
}
