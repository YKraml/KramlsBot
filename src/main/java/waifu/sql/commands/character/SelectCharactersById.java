package waifu.sql.commands.character;

import waifu.sql.commands.SQLCommandWithResult;
import waifu.sql.entry.CharacterEntrySet;

public class SelectCharactersById extends SQLCommandWithResult<CharacterEntrySet> {

    private final String idMal;

    public SelectCharactersById(String idMal) {
        super(new CharacterEntrySet());
        this.idMal = idMal;
    }

    @Override
    protected String getCommand() {
        return "select * from KRAMLSBOT.CHARACTER where KRAMLSBOT.CHARACTER.idMal like " + idMal;
    }
}
