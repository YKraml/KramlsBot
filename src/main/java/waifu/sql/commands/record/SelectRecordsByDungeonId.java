package waifu.sql.commands.record;

import waifu.model.dungeon.Dungeon;
import waifu.sql.commands.SQLCommandWithResult;
import waifu.sql.entry.RecordEntrySet;

public class SelectRecordsByDungeonId extends SQLCommandWithResult<RecordEntrySet> {

    private final Dungeon dungeon;

    public SelectRecordsByDungeonId(Dungeon dungeon) {
        super(new RecordEntrySet());
        this.dungeon = dungeon;
    }

    @Override
    protected String getCommand() {
        return "select * from KRAMLSBOT.RECORD left join KRAMLSBOT.TEAM on TEAM.id like teamID where dungeonID like " + dungeon.getChannelId() + ";";
    }
}
