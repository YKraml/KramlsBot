package waifu.sql.commands.record;

import waifu.model.dungeon.Dungeon;
import waifu.model.dungeon.DungeonRecord;
import waifu.sql.commands.SQLCommandCheckExistence;

public class RecordExistst extends SQLCommandCheckExistence {

    private final Dungeon dungeon;
    private final DungeonRecord dungeonRecord;

    public RecordExistst(Dungeon dungeon, DungeonRecord dungeonRecord) {
        this.dungeon = dungeon;
        this.dungeonRecord = dungeonRecord;
    }

    @Override
    protected String getCommand() {
        return "select * from KRAMLSBOT.RECORD where dungeonId like "
                + "'" + dungeon.getChannelId() + "'" + " and recordType like "
                + "'" + dungeonRecord.getRecordType() + "'" + ";";
    }
}
