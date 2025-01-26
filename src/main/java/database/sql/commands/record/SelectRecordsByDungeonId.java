package database.sql.commands.record;

import database.sql.commands.SQLCommandWithResult;
import database.sql.entry.RecordEntrySet;
import domain.waifu.dungeon.Dungeon;

public class SelectRecordsByDungeonId extends SQLCommandWithResult<RecordEntrySet> {

  private final Dungeon dungeon;

  public SelectRecordsByDungeonId(Dungeon dungeon) {
    super(new RecordEntrySet());
    this.dungeon = dungeon;
  }

  @Override
  protected String getCommand() {
    return
        "select * from KRAMLSBOT.RECORD left join KRAMLSBOT.TEAM on TEAM.id like teamID where dungeonID like "
            + dungeon.getChannelId() + ";";
  }
}
