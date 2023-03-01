package waifu.sql.commands.dungeon;

import waifu.sql.commands.SQLCommandWithResult;
import waifu.sql.entry.DungeonEntrySet;

public class SelectAllDungeons extends SQLCommandWithResult<DungeonEntrySet> {

  public SelectAllDungeons() {
    super(new DungeonEntrySet());
  }

  @Override
  protected String getCommand() {
    return "select * from KRAMLSBOT.DUNGEON";
  }
}
