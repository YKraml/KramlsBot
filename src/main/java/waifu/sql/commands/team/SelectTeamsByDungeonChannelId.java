package waifu.sql.commands.team;

import waifu.sql.commands.SQLCommandWithResult;
import waifu.sql.entry.TeamEntrySet;

public class SelectTeamsByDungeonChannelId extends SQLCommandWithResult<TeamEntrySet> {

  private final String dungeonChannelId;

  public SelectTeamsByDungeonChannelId(String dungeonChannelId) {
    super(new TeamEntrySet());
    this.dungeonChannelId = dungeonChannelId;
  }

  @Override
  protected String getCommand() {
    return "select * from KRAMLSBOT.TEAM where dungeon = '%s';".formatted(dungeonChannelId);
  }
}
