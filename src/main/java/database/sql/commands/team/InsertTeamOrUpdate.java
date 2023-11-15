package database.sql.commands.team;

import java.util.Locale;

import database.sql.commands.SQLCommandWithoutResult;
import domain.waifu.dungeon.Team;

public class InsertTeamOrUpdate extends SQLCommandWithoutResult {

  private final Team team;

  public InsertTeamOrUpdate(Team team) {
    this.team = team;
  }

  @Override
  protected String getCommand() {
    String id = team.getId();
    boolean inDungeon = team.isInDungeon();
    long money = team.getInventory().getMoney();
    long cookies = team.getInventory().getCookies();
    long stardust = team.getInventory().getStardust();
    String channelId = team.getCurrentDungeon().getChannelId();
    int teamSize = team.getTeamSize();
    String name = team.getName();
    String playerId = team.getPlayer().getId();
    int level = team.getLevel();
    long morphStones = team.getInventory().getMorphStones();

    return String.format(Locale.US,
        "insert into KRAMLSBOT.TEAM values ('%s','%s','%s',%d,%b,%d,%d,%d,'%s',%d, %d) on duplicate key update name = '%s',teamsize = %d,isInDungeon = %b,money = %d,stardust = %d,cookies = %d,dungeon = '%s',level = %d, morphStones = %d;",
        id, name, playerId, teamSize, inDungeon, money, stardust, cookies,
        channelId, level, morphStones,
        name, teamSize, inDungeon, money, stardust, cookies, channelId, level, morphStones);
  }
}
