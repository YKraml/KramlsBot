package database.sql.commands.character;

import database.sql.commands.SQLCommandWithResult;
import database.sql.entry.CharacterEntrySet;

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
