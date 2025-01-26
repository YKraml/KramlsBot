package database.sql.entry;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamFighterEntrySet extends AbstractEntrySet<TeamFighterEntrySet.TeamFighterEntry> {


  @Override
  public void addSingleResult(ResultSet resultSet) throws SQLException {

    String idTeam = resultSet.getString(1);
    String idWaifu = resultSet.getString(2);
    int live = resultSet.getInt(3);
    this.addEntry(new TeamFighterEntry(idTeam, idWaifu, live));
  }

  public static class TeamFighterEntry extends AbstractEntry {

    private final String idTeam;
    private final String idWaifu;
    private final int live;

    public TeamFighterEntry(String idTeam, String idWaifu, int live) {
      this.idTeam = idTeam;
      this.idWaifu = idWaifu;
      this.live = live;
    }

    public String getIdTeam() {
      return idTeam;
    }

    public String getIdWaifu() {
      return idWaifu;
    }

    public int getLive() {
      return live;
    }
  }
}
