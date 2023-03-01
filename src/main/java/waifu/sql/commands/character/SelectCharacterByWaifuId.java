package waifu.sql.commands.character;

import waifu.sql.commands.SQLCommandWithResult;
import waifu.sql.entry.CharacterEntrySet;

public class SelectCharacterByWaifuId extends SQLCommandWithResult<CharacterEntrySet> {

    private final String waifuId;

    public SelectCharacterByWaifuId(String waifuId) {
        super(new CharacterEntrySet());
        this.waifuId = waifuId;
    }

    @Override
    protected String getCommand() {
        return "select * from KRAMLSBOT.CHARACTER left join KRAMLSBOT.WAIFU on CHARACTER.idMal = WAIFU.idMal where WAIFU.id like " + "'"
            + waifuId + "'" + ";";
    }
}
