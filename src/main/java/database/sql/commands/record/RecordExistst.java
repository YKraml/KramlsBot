package database.sql.commands.record;

import database.sql.commands.SQLCommandCheckExistence;
import domain.waifu.dungeon.Dungeon;
import domain.waifu.dungeon.DungeonRecord;

public class RecordExistst extends SQLCommandCheckExistence {

  private final Dungeon dungeon;
  private final DungeonRecord dungeonRecord;

  public RecordExistst(Dungeon dungeon, DungeonRecord dungeonRecord) {
    this.dungeon = dungeon;
    this.dungeonRecord = dungeonRecord;
  }

  @Override
  protected String getCommand() {
    return "select * from KRAMLSBOT.RECORD where dungeonId like "
        + "'" + dungeon.getChannelId() + "'" + " and recordType like "
        + "'" + dungeonRecord.getRecordType() + "'" + ";";
  }
}
