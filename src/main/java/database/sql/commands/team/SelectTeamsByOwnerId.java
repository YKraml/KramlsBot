package database.sql.commands.team;

import database.sql.commands.SQLCommandWithResult;
import database.sql.entry.TeamEntrySet;

public class SelectTeamsByOwnerId extends SQLCommandWithResult<TeamEntrySet> {

  private final String playerId;

  public SelectTeamsByOwnerId(String playerId) {
    super(new TeamEntrySet());
    this.playerId = playerId;
  }

  @Override
  protected String getCommand() {
    return "select * from KRAMLSBOT.TEAM where player = '%s';".formatted(playerId);
  }
}
