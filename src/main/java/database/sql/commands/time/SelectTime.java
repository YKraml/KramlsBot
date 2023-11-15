package database.sql.commands.time;

import database.sql.commands.SQLCommandWithResult;
import database.sql.entry.TimeEntrySet;

public class SelectTime extends SQLCommandWithResult<TimeEntrySet> {

    private final String userId;

    public SelectTime(String userId) {
        super(new TimeEntrySet());
        this.userId = userId;
    }

    @Override
    protected String getCommand() {
        return "select * from KRAMLSBOT.TIME where idUser like " + "'" + userId + "'" + ";";
    }
}
