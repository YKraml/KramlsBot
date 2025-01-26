package database.sql.commands.user;

import database.sql.commands.SQLCommandWithResult;
import database.sql.entry.UserEntrySet;

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

