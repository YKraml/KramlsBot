package waifu.sql.commands.record;

import waifu.sql.commands.SQLCommandWithoutResult;
import waifu.model.dungeon.Dungeon;
import waifu.model.dungeon.Record;

public class AlterRecord extends SQLCommandWithoutResult {

    private final Dungeon dungeon;
    private final Record record;

    public AlterRecord(Dungeon dungeon, Record record) {
        this.dungeon = dungeon;
        this.record = record;
    }

    @Override
    protected String getCommand() {
        return "update KRAMLSBOT.RECORD" +
                " set value = " + record.getValue() + "," +
                " teamId = " + record.getTeam().getId() +
                " where dungeonId like " + "'" + dungeon.getChannelId() + "'" + " and recordType like " + "'"
            + record.getRecordType() + "'" + ";";
    }
}
