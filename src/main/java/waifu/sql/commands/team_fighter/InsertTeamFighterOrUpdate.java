package waifu.sql.commands.team_fighter;

import waifu.model.dungeon.Team;
import waifu.model.fighting.Fighter;
import waifu.sql.commands.SQLCommandWithoutResult;

public class InsertTeamFighterOrUpdate extends SQLCommandWithoutResult {


  private final Team team;
  private final Fighter fighter;

  public InsertTeamFighterOrUpdate(Team team, Fighter fighter) {
    this.team = team;
    this.fighter = fighter;
  }

  @Override
  protected String getCommand() {
    return "insert into KRAMLSBOT.TEAM_FIGHTER values ('%s','%s',%d) on duplicate key update  live = %d".formatted(
        team.getId(), fighter.getWaifu().getId(), fighter.getCurrentHp(), fighter.getCurrentHp());
  }


}
