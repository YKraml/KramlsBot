package logic.waifu;

import com.google.inject.Inject;
import domain.waifu.Player;
import domain.waifu.dungeon.Dungeon;
import domain.waifu.dungeon.Inventory;
import domain.waifu.dungeon.Team;
import domain.waifu.fighting.Fighter;
import database.sql.SQLCommandExecutor;
import database.sql.commands.team.InsertTeamOrUpdate;
import database.sql.commands.team.SelectTeamsByDungeonChannelId;
import database.sql.commands.team.SelectTeamsByOwnerId;
import database.sql.commands.team_fighter.DeleteTeamFighter;
import database.sql.commands.team_fighter.InsertTeamFighterOrUpdate;
import database.sql.commands.team_fighter.SelectWaifusFromTeam;
import database.sql.entry.TeamEntrySet;
import database.sql.entry.TeamFighterEntrySet;
import domain.exceptions.MyOwnException;
import java.util.Optional;
import javax.inject.Singleton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Singleton
public final class TeamLoaderSql implements TeamLoader {

  private final List<Team> teamCache;

  private final DungeonLoader dungeonLoader;
  private final SQLCommandExecutor sqlCommandExecutor;

  @Inject
  public TeamLoaderSql(DungeonLoader dungeonLoader, SQLCommandExecutor sqlCommandExecutor) {
    this.dungeonLoader = dungeonLoader;
    this.sqlCommandExecutor = sqlCommandExecutor;
    teamCache = Collections.synchronizedList(new ArrayList<>());

  }

  @Override
  public void saveTeam(Team team) throws MyOwnException {
    sqlCommandExecutor.execute(new InsertTeamOrUpdate(team));
    for (Fighter fighter : new ArrayList<>(team.getFighters())) {
      sqlCommandExecutor.execute(new InsertTeamFighterOrUpdate(team, fighter));
    }
  }

  @Override
  public void deleteTeamFighter(Fighter fighter) throws MyOwnException {
    sqlCommandExecutor.execute(new DeleteTeamFighter(fighter.getWaifu()));
  }

  @Override
  public List<Team> getTeamsFromPlayer(Player player) throws MyOwnException {
    TeamEntrySet teamEntrySet = sqlCommandExecutor.execute(
        new SelectTeamsByOwnerId(player.getId()));
    List<Team> teamList = new ArrayList<>();
    for (TeamEntrySet.TeamEntry entry : teamEntrySet) {
      teamList.add(createTeamFromEntry(player, entry));
    }
    return teamList;
  }

  @Override
  public List<Team> getTeamsInDungeon(String channelId, PlayerLoader playerLoader)
      throws MyOwnException {
    TeamEntrySet teamEntrySet = sqlCommandExecutor.execute(
        new SelectTeamsByDungeonChannelId(channelId));
    List<Team> teamList = new ArrayList<>();
    for (TeamEntrySet.TeamEntry entry : teamEntrySet) {
      Player player = playerLoader.getPlayerById(entry.getPlayerId());
      teamList.add(createTeamFromEntry(player, entry));
    }
    return teamList;
  }

  private Team createTeamFromEntry(Player player, TeamEntrySet.TeamEntry entry) throws MyOwnException {

    Optional<Team> cachedTeam = teamCache.stream()
        .filter(team -> team.getId().equals(entry.getId())).findFirst();
    if (cachedTeam.isPresent()) {
      return cachedTeam.get();
    }

    Team team = new Team(entry.getId(), entry.getName(), player, entry.getTeamsize(),
        new Inventory(entry.getMoney(), entry.getStardust(), entry.getCookies(), entry.getMorphStones()));

    TeamFighterEntrySet teamFighterEntrySet = sqlCommandExecutor.execute(
        new SelectWaifusFromTeam(team));
    teamFighterEntrySet.forEach(teamFighterEntry -> player.getWaifuList().stream()
        .filter(w -> w.getId().equals(teamFighterEntry.getIdWaifu())).findFirst()
        .ifPresent(w -> team.addWaifToTeamWithoutChecks(w, teamFighterEntry.getLive())));

    Optional<Dungeon> dungeon = dungeonLoader.getDungeon(entry.getDungeon());
    if (dungeon.isPresent()) {
      team.entersDungeon(dungeon.get(), entry.getLevel());
    }

    teamCache.add(team);
    return team;
  }

}