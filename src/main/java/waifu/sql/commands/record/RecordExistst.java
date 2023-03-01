package waifu.sql.commands.record;

import waifu.model.dungeon.Dungeon;
import waifu.model.dungeon.Record;
import waifu.sql.commands.SQLCommandCheckExistence;

public class RecordExistst extends SQLCommandCheckExistence {

    private final Dungeon dungeon;
    private final Record record;

    public RecordExistst(Dungeon dungeon, Record record) {
        this.dungeon = dungeon;
        this.record = record;
    }

    @Override
    protected String getCommand() {
        return "select * from KRAMLSBOT.RECORD where dungeonId like "
                + "'" + dungeon.getChannelId() + "'" + " and recordType like "
                + "'" + record.getRecordType() + "'" + ";";
    }
}
