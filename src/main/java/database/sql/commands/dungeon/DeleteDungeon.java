package database.sql.commands.dungeon;

import database.sql.commands.SQLCommandWithoutResult;
import domain.waifu.dungeon.Dungeon;

public class DeleteDungeon extends SQLCommandWithoutResult {

  private final Dungeon dungeon;
  public DeleteDungeon(Dungeon dungeon) {
    this.dungeon = dungeon;
  }

  @Override
  protected String getCommand() {
    return "delete from KRAMLSBOT.DUNGEON where channelID = '%s';".formatted(dungeon.getChannelId());
  }
}
