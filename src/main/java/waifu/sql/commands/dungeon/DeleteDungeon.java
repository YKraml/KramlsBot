package waifu.sql.commands.dungeon;

import waifu.model.dungeon.Dungeon;
import waifu.sql.commands.SQLCommandWithoutResult;

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
