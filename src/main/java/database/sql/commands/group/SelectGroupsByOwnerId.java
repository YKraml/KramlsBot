package database.sql.commands.group;

import database.sql.commands.SQLCommandWithResult;
import database.sql.entry.GroupEntrySet;
import domain.waifu.Player;

public class SelectGroupsByOwnerId extends SQLCommandWithResult<GroupEntrySet> {

  private final Player player;

  public SelectGroupsByOwnerId(Player player) {
    super(new GroupEntrySet());
    this.player = player;
  }


  @Override
  protected String getCommand() {
    return "select * from KRAMLSBOT.GROUP where owner like " + "'" + player.getId() + "'" + ";";
  }
}
