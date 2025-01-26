package database.sql.commands.team_fighter;

import database.sql.commands.SQLCommandWithoutResult;
import domain.waifu.Waifu;

public class DeleteTeamFighter extends SQLCommandWithoutResult {

  private final Waifu waifu;

  public DeleteTeamFighter(Waifu waifu) {
    this.waifu = waifu;
  }

  @Override
  protected String getCommand() {
    return "delete from KRAMLSBOT.TEAM_FIGHTER where KRAMLSBOT.TEAM_FIGHTER.idWaifu like " + "'"
        + waifu.getId() + "'" + ";";
  }
}
