package waifu.sql.commands.dungeon;

import waifu.model.dungeon.Dungeon;
import waifu.sql.commands.SQLCommandWithoutResult;

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
