package database.sql.commands.waifu;

import database.sql.commands.SQLCommandWithResult;
import database.sql.entry.WaifuCharacterEntrySet;

public class SelectWaifuJoinedCharacter extends SQLCommandWithResult<WaifuCharacterEntrySet> {

  private final String ownerId;

  public SelectWaifuJoinedCharacter(String ownerId) {
    super(new WaifuCharacterEntrySet());
    this.ownerId = ownerId;
  }

  @Override
  protected String getCommand() {
    return "select * from KRAMLSBOT.WAIFU join KRAMLSBOT.CHARACTER using (idMal) where owner = '%s';".formatted(
        ownerId);
  }
}
