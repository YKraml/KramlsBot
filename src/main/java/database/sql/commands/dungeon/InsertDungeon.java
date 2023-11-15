package database.sql.commands.dungeon;

import database.sql.commands.SQLCommandWithoutResult;
import domain.waifu.dungeon.Dungeon;

public class InsertDungeon extends SQLCommandWithoutResult {

  private final Dungeon dungeon;

  public InsertDungeon(Dungeon dungeon) {
    this.dungeon = dungeon;
  }

  @Override
  protected String getCommand() {
    String pattern = "insert into KRAMLSBOT.DUNGEON values ('%s','%s','%s','%s')";
    return pattern.formatted(dungeon.getChannelId(), dungeon.getName(), dungeon.getDifficulty(), dungeon.getServerId());
  }
}
