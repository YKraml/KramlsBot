package waifu.loader;

import de.kraml.Terminal;
import exceptions.MyOwnException;
import exceptions.messages.CouldNotLoadPlayer;
import exceptions.messages.CouldNotSaveLikedSong;
import exceptions.messages.CouldNotSavePlayer;
import exceptions.messages.CouldNotSaveTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Singleton;
import music.queue.QueueElement;
import waifu.model.Group;
import waifu.model.Player;
import waifu.model.Waifu;
import waifu.model.dungeon.Team;
import waifu.sql.SQLCommandExecutor;
import waifu.sql.commands.battle_waifu.AlterBattleWaifu;
import waifu.sql.commands.battle_waifu.BattleWaifuExists;
import waifu.sql.commands.battle_waifu.InsertBattleWaifu;
import waifu.sql.commands.battle_waifu.SelectBattleWaifuByUserId;
import waifu.sql.commands.songs.InsertLikedSong;
import waifu.sql.commands.songs.LikedSongExists;
import waifu.sql.commands.songs.SelectLikedSongs;
import waifu.sql.commands.time.InsertTimeOrUpdate;
import waifu.sql.commands.time.SelectTime;
import waifu.sql.commands.user.AlterUser;
import waifu.sql.commands.user.InsertUser;
import waifu.sql.commands.user.SelectUsersById;
import waifu.sql.commands.user.UserExists;
import waifu.sql.entry.AbstractEntrySet;
import waifu.sql.entry.BattleWaifuEntrySet.BattleWaifuEntry;
import waifu.sql.entry.LikedSongEntrySet;
import waifu.sql.entry.TimeEntrySet;
import waifu.sql.entry.UserEntrySet;
import waifu.sql.mapper.LikedSongMapper;
import waifu.sql.mapper.PlayerMapper;

@Singleton
public class PlayerLoaderSql implements PlayerLoader {

  public static final String NEW_PLAYER_SAVED = "Saved new Player. Name = '%s', Id = '%s'";
  public static final String EXISTING_PLAYER_SAVED = "Saved existing Player. Name = '%s', Id = '%s'";
  private final TeamLoader teamLoader;
  private final WaifuLoader waifuLoader;
  private final GroupLoader groupLoader;

  private final SQLCommandExecutor sqlCommandExecutor;

  public PlayerLoaderSql(TeamLoader teamLoader, WaifuLoader waifuLoader, GroupLoader groupLoader,
      SQLCommandExecutor sqlCommandExecutor) {
    this.teamLoader = teamLoader;
    this.waifuLoader = waifuLoader;
    this.groupLoader = groupLoader;
    this.sqlCommandExecutor = sqlCommandExecutor;
  }

  @Override
  public void savePlayer(Player player) throws MyOwnException {

    try {
      if (!sqlCommandExecutor.execute(new UserExists(player))) {
        sqlCommandExecutor.execute(new InsertUser(player));
        Terminal.printLine(NEW_PLAYER_SAVED.formatted(player.getName(), player.getId()));
      } else {
        sqlCommandExecutor.execute(new AlterUser(player));
        saveWaifus(player);
        saveBattleWaifu(player);
        saveGroups(player);
        saveTeams(player);
        saveTimes(player);
        saveSongs(player);
        Terminal.printLine(EXISTING_PLAYER_SAVED.formatted(player.getName(), player.getId()));
      }
    } catch (MyOwnException e) {
      throw new MyOwnException(new CouldNotSavePlayer(player), e);
    }

  }

  @Override
  public Player getPlayerById(String userId) throws MyOwnException {

    try {

      synchronized (this) {

        UserEntrySet userEntrySet = sqlCommandExecutor.execute(new SelectUsersById(userId));
        Optional<Player> playerOptional = new PlayerMapper(userEntrySet).getFirst();

        if (playerOptional.isEmpty()) {
          return new Player(userId, "TEMP_NAME");
        }

        Player player = playerOptional.get();
        loadWaifus(player);
        loadBattleWaifu(player);
        loadGroups(player);
        loadTeams(player);
        loadTimes(userId, player);
        loadSongs(userId, player);

        return player;
      }
    } catch (MyOwnException e) {
      throw new MyOwnException(new CouldNotLoadPlayer(userId), e);
    }
  }

  private void saveWaifus(Player player) throws MyOwnException {
    List<Waifu> copiedWaifuList = new ArrayList<>(player.getWaifuList());
    for (Waifu waifu : copiedWaifuList) {
      waifuLoader.saveWaifu(waifu, player);
    }
  }

  private void saveBattleWaifu(Player player) throws MyOwnException {
    if (player.getBattleWaifu().isPresent()) {
      if (sqlCommandExecutor.execute(new BattleWaifuExists(player))) {
        sqlCommandExecutor.execute(new AlterBattleWaifu(player.getBattleWaifu().get(), player));
      } else if (player.getBattleWaifu().isPresent()) {
        sqlCommandExecutor.execute(new InsertBattleWaifu(player.getBattleWaifu().get(), player));
      }
    }
  }

  private void saveGroups(Player player) throws MyOwnException {
    List<Group> copiedGroupList = new ArrayList<>(player.getGroupList());
    for (Group group : copiedGroupList) {
      groupLoader.saveGroup(group, player);
    }
  }

  private void saveTeams(Player player) throws MyOwnException {
    List<Team> copyTeamList = new ArrayList<>(player.getTeamList());
    for (Team team : copyTeamList) {
      teamLoader.saveTeam(team);
    }
  }

  private void saveTimes(Player player) throws MyOwnException {
    Map<String, Long> copyServerTimeMap = new ConcurrentHashMap<>(player.getTimeOnServers());
    for (Map.Entry<String, Long> serverTimeEntry : copyServerTimeMap.entrySet()) {
      saveTime(player.getId(), serverTimeEntry.getKey(), serverTimeEntry.getValue());
    }
  }

  private void saveSongs(Player player) throws MyOwnException {
    List<QueueElement> likedSongsCopy = new ArrayList<>(player.getLikedSongs());
    for (QueueElement queueElement : likedSongsCopy) {
      saveLikedSong(player.getId(), queueElement);
    }
  }

  private void loadWaifus(Player player) throws MyOwnException {
    List<Waifu> waifuList = waifuLoader.getWaifusFromPlayer(player);
    waifuList.forEach(player::addWaifuWithoutChecks);
  }

  private void loadSongs(String userId, Player player) throws MyOwnException {
    LikedSongEntrySet likedSongEntrySet = sqlCommandExecutor.execute(new SelectLikedSongs(userId));
    List<QueueElement> queueElements = new LikedSongMapper(likedSongEntrySet,
        player.getName()).getList();
    for (QueueElement element : queueElements) {
      player.addQueueElement(element);
    }
  }

  private void loadTimes(String userId, Player player) throws MyOwnException {
    TimeEntrySet timeEntrySet = sqlCommandExecutor.execute(new SelectTime(userId));
    for (TimeEntrySet.TimeEntry timeEntry : timeEntrySet) {
      player.addToTimeOnServer(timeEntry.getIdServer(), timeEntry.getTime());
    }
  }

  private void loadTeams(Player player) throws MyOwnException {
    List<Team> teamList = teamLoader.getTeamsFromPlayer(player);
    for (Team team : teamList) {
      player.addTeam(team);
    }
  }

  private void loadGroups(Player player) throws MyOwnException {
    List<Group> groupList = groupLoader.getGroupsFromPlayer(player);
    for (Group group : groupList) {
      player.addGroup(group);
    }
  }

  private void loadBattleWaifu(Player player) throws MyOwnException {
    AbstractEntrySet<BattleWaifuEntry> battleWaifuEntrySet = sqlCommandExecutor.execute(
        new SelectBattleWaifuByUserId(
            player));
    Optional<BattleWaifuEntry> battleWaifuEntry = battleWaifuEntrySet.getFirst();
    if (battleWaifuEntry.isPresent()) {
      Optional<Waifu> waifu = waifuLoader.getWaifuById(battleWaifuEntry.get().getWaifuId());
      waifu.ifPresent(player::setBattleWaifu);
    }
    if (!player.getWaifuList().isEmpty() && player.getBattleWaifu().isEmpty()) {
      player.setBattleWaifu(player.getWaifuList().get(0));
    }
  }


  private void saveTime(String userId, String serverId, Long time) throws MyOwnException {
    try {
      sqlCommandExecutor.execute(new InsertTimeOrUpdate(userId, serverId, time));
    } catch (MyOwnException e) {
      throw new MyOwnException(new CouldNotSaveTime(serverId, userId), e);
    }
  }

  private void saveLikedSong(String userId, QueueElement queueElement) throws MyOwnException {
    try {
      if (!sqlCommandExecutor.execute(new LikedSongExists(userId, queueElement.getUrl()))) {
        sqlCommandExecutor.execute(new InsertLikedSong(userId, queueElement));
      }
    } catch (MyOwnException e) {
      throw new MyOwnException(new CouldNotSaveLikedSong(userId, queueElement.getName()), e);
    }
  }

}
