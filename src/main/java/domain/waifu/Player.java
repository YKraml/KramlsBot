package domain.waifu;

import domain.exceptions.MyOwnException;
import domain.exceptions.messages.CouldNotFindWaifu;
import domain.exceptions.messages.ReachedMaxWaifus;
import domain.exceptions.messages.WaifuAlreadyInList;
import domain.queue.QueueElement;
import domain.waifu.dungeon.Inventory;
import domain.waifu.dungeon.Team;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;


public class Player {

  private final String userId;
  private final Map<String, Long> timeOnServers;
  private final Inventory inventory;
  private final List<Waifu> waifuList;
  private final List<Group> groupList;
  private final List<Team> teamList;
  private final List<QueueElement> likedSongs;
  private String name;
  private int rightGuesses;
  private String lastDaily;
  private int maxWaifus;

  public Player(String userId, String name, int rightGuesses, String lastDaily, Inventory inventory,
      int maxWaifus) {
    this.userId = userId;
    this.timeOnServers = Collections.synchronizedMap(new HashMap<>());
    this.name = name;
    this.rightGuesses = rightGuesses;
    this.lastDaily = lastDaily;
    this.waifuList = Collections.synchronizedList(new ArrayList<>());
    this.groupList = Collections.synchronizedList(new ArrayList<>());
    this.teamList = Collections.synchronizedList(new ArrayList<>());
    this.likedSongs = Collections.synchronizedList(new ArrayList<>());
    this.inventory = inventory;
    this.maxWaifus = maxWaifus;
  }

  public Player(String userId, String name) {
    this(userId, name, 0, "1.1.2000", new Inventory(), 30);
  }

  public static Player createEmptyPlayer() {
    return new Player("1234", "Temporärer Name", 42, "1234",
        new Inventory(100000, 100000, 100000, 100), 100);
  }

  public String getLastDaily() {
    return lastDaily;
  }

  public void setLastDaily(String lastDaily) {
    this.lastDaily = lastDaily;
  }

  public String getId() {
    return userId;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getNameTag() {
    return "<@!" + this.getId() + ">";
  }

  public Long getTimeOnServer(String server) {
    return timeOnServers.getOrDefault(server, 0L);
  }

  public Inventory getInventory() {
    return inventory;
  }

  public int getRightGuesses() {
    return rightGuesses;
  }

  public List<Waifu> getWaifuList() {
    return Collections.unmodifiableList(waifuList);
  }

  public List<Group> getGroupList() {
    return Collections.unmodifiableList(groupList);
  }

  public List<Team> getTeamList() {
    return Collections.unmodifiableList(teamList);
  }

  public void addToGuessesRight(int rightGuesses) {
    this.rightGuesses += rightGuesses;
  }

  public void addToTimeOnServer(String serverId, long time) {
    timeOnServers.put(serverId, timeOnServers.getOrDefault(serverId, 0L) + time);
  }

  public Optional<Group> getGroupByName(String name) {
    Predicate<Group> filter = group -> group.getName().equalsIgnoreCase(name);
    return groupList.stream().filter(filter).findFirst();

  }

  public Optional<Team> getTeamByName(String teamName) {
    Predicate<Team> filter = team -> team.getName().equalsIgnoreCase(teamName);
    return teamList.stream().filter(filter).findFirst();

  }

  public void addGroup(Group newGroup) throws MyOwnException {

    if (getTeamByName(newGroup.getName()).isPresent()) {
      throw new MyOwnException(() -> "Gruppe '%s' existiert schon.".formatted(newGroup.getName()),
          null);
    }

    this.groupList.add(newGroup);

  }

  public void addTeam(Team team) {
    this.teamList.add(team);
  }

  public void addWaifu(Waifu waifu) throws MyOwnException {

    if (this.maxWaifus <= this.waifuList.size()) {
      throw new MyOwnException(new ReachedMaxWaifus(this), null);
    }

    synchronized (this.waifuList) {
      if (this.waifuList.contains(waifu)) {
        throw new MyOwnException(new WaifuAlreadyInList(waifu), null);
      }
      this.waifuList.add(waifu);
    }
  }

  public void addWaifuWithoutChecks(Waifu waifu) {
    this.waifuList.add(waifu);
  }


  public void deleteGroup(Group group) {
    this.groupList.remove(group);
  }

  public void deleteWaifu(Waifu waifu) throws MyOwnException {
    this.groupList.forEach(group -> group.removeWaifu(waifu));
    for (Team team : this.teamList) {
      team.removeWaifu(waifu);
    }
    this.waifuList.remove(waifu);
  }

  public void sortWaifuList(Comparator<Waifu> comparator) {
    this.waifuList.sort(comparator);
  }

  public int getMaxWaifus() {
    return maxWaifus;
  }

  public void expandMaxWaifus(int n) {
    this.maxWaifus += n;
  }

  public Map<String, Long> getTimeOnServers() {
    return timeOnServers;
  }

  public void addQueueElement(QueueElement queueElement) {
    if (this.likedSongs.contains(queueElement)) {
      return;
    }
    this.likedSongs.add(queueElement);
  }

  public List<QueueElement> getLikedSongs() {
    return likedSongs;
  }

  public long getTimeSum() {
    return timeOnServers.values().stream().mapToLong(l -> l).sum();
  }

  public Waifu getWaifu(int waifuId) throws MyOwnException {
    if (waifuList.size() <= waifuId || waifuId < 0) {
      throw new MyOwnException(new CouldNotFindWaifu(waifuId), null);
    }
    return waifuList.get(waifuId);
  }
}


