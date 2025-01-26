package database.sql.commands.record;

import database.sql.commands.SQLCommandWithoutResult;
import domain.waifu.dungeon.Dungeon;
import domain.waifu.dungeon.DungeonRecord;

public class InsertRecord extends SQLCommandWithoutResult {


  private final Dungeon dungeon;
  private final DungeonRecord dungeonRecord;

  public InsertRecord(Dungeon dungeon, DungeonRecord dungeonRecord) {
    this.dungeon = dungeon;
    this.dungeonRecord = dungeonRecord;
  }

  @Override
  protected String getCommand() {
    return "insert into KRAMLSBOT.RECORD values ("
        + "'" + dungeonRecord.getTeam().getId() + "'" + ","
        + "'" + dungeon.getChannelId() + "'" + ","
        + "'" + dungeonRecord.getRecordType() + "'" + ","
        + dungeonRecord.getValue()
        + ");";
  }
}
