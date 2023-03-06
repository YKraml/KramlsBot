package waifu.model;

import exceptions.MyOwnException;
import exceptions.messages.CouldNotFindWaifu;
import exceptions.messages.ReachedMaxWaifus;
import exceptions.messages.WaifuAlreadyInList;
import java.util.function.Predicate;
import music.queue.QueueElement;
import waifu.model.dungeon.Inventory;
import waifu.model.dungeon.Team;

import java.util.*;

public class Player {

  private final String userId;
  private String name;
  private final Map<String, Long> timeOnServers;
  private int rightGuesses;
  private String lastDaily;
  private final Inventory inventory;
  private final List<Waifu> waifuList;
  private final List<Group> groupList;
  private final List<Team> teamList;
  private final List<QueueElement> likedSongs;
  private Waifu battleWaifu;
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
    return new Player("1234", "Temporärer Name", 1, "1234", new Inventory(10000, 10000, 10000,
        100), 1);
  }

  public String getLastDaily() {
    return lastDaily;
  }

  public String getId() {
    return userId;
  }

  public String getName() {
    return this.name;
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

  public Optional<Waifu> getBattleWaifu() {
    return Optional.ofNullable(battleWaifu);
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

  public void setLastDaily(String lastDaily) {
    this.lastDaily = lastDaily;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setBattleWaifu(Waifu battleWaifu) {
    this.battleWaifu = battleWaifu;
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


  public boolean deleteGroup(Group group) {
    return this.groupList.remove(group);
  }

  public boolean deleteWaifu(Waifu waifu) throws MyOwnException {
    this.groupList.forEach(group -> group.removeWaifu(waifu));
    for (Team team : this.teamList) {
      team.removeWaifu(waifu);
    }
    return this.waifuList.remove(waifu);
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


