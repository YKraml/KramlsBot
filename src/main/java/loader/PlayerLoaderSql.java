package loader;

import com.google.inject.Inject;
import database.sql.SQLCommandExecutor;
import database.sql.commands.songs.InsertLikedSong;
import database.sql.commands.songs.LikedSongExists;
import database.sql.commands.songs.SelectLikedSongs;
import database.sql.commands.time.InsertTimeOrUpdate;
import database.sql.commands.time.SelectTime;
import database.sql.commands.user.AlterUser;
import database.sql.commands.user.InsertUser;
import database.sql.commands.user.SelectUsersById;
import database.sql.commands.user.UserExists;
import database.sql.entry.LikedSongEntrySet;
import database.sql.entry.TimeEntrySet;
import database.sql.entry.UserEntrySet;
import database.sql.mapper.LikedSongMapper;
import database.sql.mapper.PlayerMapper;
import domain.GroupLoader;
import domain.PlayerLoader;
import domain.TeamLoader;
import domain.WaifuLoader;
import domain.exceptions.MyOwnException;
import domain.exceptions.messages.CouldNotLoadPlayer;
import domain.exceptions.messages.CouldNotSaveLikedSong;
import domain.exceptions.messages.CouldNotSavePlayer;
import domain.exceptions.messages.CouldNotSaveTime;
import domain.queue.QueueElement;
import domain.waifu.Group;
import domain.waifu.Player;
import domain.waifu.Waifu;
import domain.waifu.dungeon.Team;
import util.Terminal;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class PlayerLoaderSql implements PlayerLoader {

    public static final String NEW_PLAYER_SAVED = "Saved new Player. Name = '%s', Id = '%s'";
    public static final String EXISTING_PLAYER_SAVED = "Saved existing Player. Name = '%s', Id = '%s'";
    private final TeamLoader teamLoader;
    private final WaifuLoader waifuLoader;
    private final GroupLoader groupLoader;

    private final SQLCommandExecutor sqlCommandExecutor;

    @Inject
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
                saveGroups(player);
                saveTeams(player);
                saveTimes(player);
                saveSongs(player);
                Terminal.printLine(EXISTING_PLAYER_SAVED.formatted(player.getName(), player.getId()));
            }
        } catch (MyOwnException e) {
            e.printStackTrace();
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
