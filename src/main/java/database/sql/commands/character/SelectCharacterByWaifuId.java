package database.sql.commands.character;

import database.sql.commands.SQLCommandWithResult;
import database.sql.entry.CharacterEntrySet;

public class SelectCharacterByWaifuId extends SQLCommandWithResult<CharacterEntrySet> {

  private final String waifuId;

  public SelectCharacterByWaifuId(String waifuId) {
    super(new CharacterEntrySet());
    this.waifuId = waifuId;
  }

  @Override
  protected String getCommand() {
    return
        "select * from KRAMLSBOT.CHARACTER left join KRAMLSBOT.WAIFU on CHARACTER.idMal = WAIFU.idMal where WAIFU.id like "
            + "'"
            + waifuId + "'" + ";";
  }
}
