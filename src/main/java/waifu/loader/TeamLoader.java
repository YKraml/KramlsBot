package waifu.loader;

import exceptions.MyOwnException;
import java.util.List;
import waifu.model.Player;
import waifu.model.dungeon.Team;
import waifu.model.fighting.Fighter;

public interface TeamLoader {

  void saveTeam(Team team) throws MyOwnException;

  void deleteTeamFighter(Fighter fighter) throws MyOwnException;

  List<Team> getTeamsFromPlayer(Player player) throws MyOwnException;

  List<Team> getTeamsInDungeon(String channelId, PlayerLoader playerLoader)
      throws MyOwnException;
}
