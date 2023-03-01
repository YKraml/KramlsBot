package waifu.sql.commands.time;

import waifu.sql.commands.SQLCommandWithoutResult;

public class InsertTimeOrUpdate extends SQLCommandWithoutResult {

    private final String userId;
    private final String serverId;
    private final Long time;

    public InsertTimeOrUpdate(String userId, String serverId, Long time) {
        this.userId = userId;
        this.serverId = serverId;
        this.time = time;
    }

    @Override
    protected String getCommand() {
        return "insert into KRAMLSBOT.TIME values ("
                + "'" + userId + "'" + ","
                + "'" + serverId + "'" + ","
                + time
                + ") on duplicate key update " +
                "time = " + time
                + ";";
    }
}
