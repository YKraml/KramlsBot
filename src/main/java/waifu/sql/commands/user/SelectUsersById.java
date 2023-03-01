package waifu.sql.commands.user;

import waifu.sql.commands.SQLCommandWithResult;
import waifu.sql.entry.UserEntrySet;

public class SelectUsersById extends SQLCommandWithResult<UserEntrySet> {

    private final String userID;

    public SelectUsersById(String userID) {
        super(new UserEntrySet());
        this.userID = userID;
    }

    @Override
    protected String getCommand() {
        return "select * from KRAMLSBOT.USER where id like '" + userID + "';";
    }
}

