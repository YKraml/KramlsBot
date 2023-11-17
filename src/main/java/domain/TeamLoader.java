package domain;

import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import domain.waifu.dungeon.Team;
import domain.waifu.fighting.Fighter;

import java.util.List;

public interface TeamLoader {

    void saveTeam(Team team) throws MyOwnException;

    void deleteTeamFighter(Fighter fighter) throws MyOwnException;

    List<Team> getTeamsFromPlayer(Player player) throws MyOwnException;

    List<Team> getTeamsInDungeon(String channelId, PlayerLoader playerLoader) throws MyOwnException;
}
