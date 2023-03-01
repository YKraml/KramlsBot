package waifu.sql.commands.record;

import waifu.model.dungeon.Dungeon;
import waifu.model.dungeon.Record;
import waifu.sql.commands.SQLCommandWithoutResult;

public class InsertRecord extends SQLCommandWithoutResult {


    private final Dungeon dungeon;
    private final Record record;

    public InsertRecord(Dungeon dungeon, Record record) {
        this.dungeon = dungeon;
        this.record = record;
    }

    @Override
    protected String getCommand() {
        return "insert into KRAMLSBOT.RECORD values ("
                + "'" + record.getTeam().getId() + "'" + ","
                + "'" + dungeon.getChannelId() + "'" + ","
                + "'" + record.getRecordType() + "'" + ","
                + record.getValue()
                + ");";
    }
}
