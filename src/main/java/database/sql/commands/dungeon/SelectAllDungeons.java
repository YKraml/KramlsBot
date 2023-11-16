package database.sql.commands.dungeon;

import database.sql.commands.SQLCommandWithResult;
import database.sql.entry.DungeonEntrySet;

public class SelectAllDungeons extends SQLCommandWithResult<DungeonEntrySet> {

    public SelectAllDungeons() {
        super(new DungeonEntrySet());
    }

    @Override
    protected String getCommand() {
        return "select * from KRAMLSBOT.DUNGEON";
    }
}
