package waifu.sql.commands.record;

import waifu.model.dungeon.Dungeon;
import waifu.model.dungeon.DungeonRecord;
import waifu.sql.commands.SQLCommandWithoutResult;

public class InsertRecord extends SQLCommandWithoutResult {


    private final Dungeon dungeon;
    private final DungeonRecord dungeonRecord;

    public InsertRecord(Dungeon dungeon, DungeonRecord dungeonRecord) {
        this.dungeon = dungeon;
        this.dungeonRecord = dungeonRecord;
    }

    @Override
    protected String getCommand() {
        return "insert into KRAMLSBOT.RECORD values ("
                + "'" + dungeonRecord.getTeam().getId() + "'" + ","
                + "'" + dungeon.getChannelId() + "'" + ","
                + "'" + dungeonRecord.getRecordType() + "'" + ","
                + dungeonRecord.getValue()
                + ");";
    }
}
