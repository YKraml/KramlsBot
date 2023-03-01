package waifu.sql.commands.waifu;

import waifu.sql.commands.SQLCommandWithResult;
import waifu.sql.entry.WaifuCharacterEntrySet;

public class SelectWaifuJoinedCharacter extends SQLCommandWithResult<WaifuCharacterEntrySet> {

  private final String ownerId;

  public SelectWaifuJoinedCharacter(String ownerId) {
    super(new WaifuCharacterEntrySet());
    this.ownerId = ownerId;
  }

  @Override
  protected String getCommand() {
    return "select * from KRAMLSBOT.WAIFU join KRAMLSBOT.CHARACTER using (idMal) where owner = '%s';".formatted(ownerId);
  }
}
