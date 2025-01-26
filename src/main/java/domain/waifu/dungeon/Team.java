package domain.waifu.dungeon;

import domain.DisplayableElement;
import domain.exceptions.MyOwnException;
import domain.exceptions.messages.TeamIsFull;
import domain.exceptions.messages.TeamIsInDungeon;
import domain.exceptions.messages.WaifuInTeam;
import domain.waifu.Player;
import domain.waifu.Waifu;
import domain.waifu.fighting.FightHistory;
import domain.waifu.fighting.Fighter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Team implements DisplayableElement {

  private final String id;
  private final Player player;
  private final List<Fighter> fighterList;
  private final List<List<FightHistory>> fightHistories;
  private final Inventory inventory;
  private final DungeonInformation dungeonInformation;
  private String name;
  private int teamSize;

  public Team(String id, String name, Player owner, int teamSize, Inventory inventory) {
    this.id = id;
    this.name = name;
    this.player = owner;
    this.teamSize = teamSize;
    this.fighterList = Collections.synchronizedList(new ArrayList<>());
    this.fightHistories = Collections.synchronizedList(new ArrayList<>());
    this.inventory = inventory;
    this.dungeonInformation = new DungeonInformation();
  }

  public Player getPlayer() {
    return player;
  }

  public void addWaifuToTeam(Waifu waifu, int live) throws MyOwnException {
    checkIfTeamIsInDungeon();

    //Überprüft, ob die Waifu schon in einem anderen Team ist
    for (Team team : player.getTeamList()) {
      for (Fighter fighter : team.getFighters()) {
        if (fighter.getWaifu().equals(waifu)) {
          throw new MyOwnException(new WaifuInTeam(team.getPlayer(), team, waifu), null);
        }
      }
    }

    //Überprüft, ob in dem Team noch Platz ist.
    if (this.fighterList.size() >= teamSize) {
      throw new MyOwnException(new TeamIsFull(this), null);
    }

    addWaifToTeamWithoutChecks(waifu, live);
  }

  public void addWaifToTeamWithoutChecks(Waifu waifu, int live) {
    Fighter fighter = new Fighter(waifu);
    fighter.setCurrentHp(live);
    this.fighterList.add(fighter);
    this.fighterList.sort((o1, o2) -> o2.getWaifu().getStatsSum() - o1.getWaifu().getStatsSum());
  }

  public void removeWaifu(Waifu waifu) throws MyOwnException {
    if (fighterList.stream().anyMatch(fighter -> fighter.getWaifu().equals(waifu))) {
      checkIfTeamIsInDungeon();
      this.fighterList.removeIf(fighter -> fighter.getWaifu().equals(waifu));
    }
  }

  public List<FightHistory> fight(Team enemyTeam) {

    List<FightHistory> fightHistoryList = new ArrayList<>();

    while (isAlive() && enemyTeam.isAlive()) {

      List<Fighter> aliveFighters1 = this.getAliveFighters();
      List<Fighter> aliveFighters2 = enemyTeam.getAliveFighters();

      Random r = ThreadLocalRandom.current();
      Fighter fighter1 = aliveFighters1.get(r.nextInt(aliveFighters1.size()));
      Fighter fighter2 = aliveFighters2.get(r.nextInt(aliveFighters2.size()));

      FightHistory history = fighter1.fight(fighter2);
      fightHistoryList.add(history);

    }
    return fightHistoryList;
  }

  private List<Fighter> getAliveFighters() {
    return this.fighterList.stream().filter(Fighter::isAlive).collect(Collectors.toList());
  }

  public boolean isAlive() {
    return !this.getAliveFighters().isEmpty();
  }

  public void addFightHistoryList(List<FightHistory> fightHistoryList) {
    this.fightHistories.add(fightHistoryList);
  }

  public List<Fighter> getFighters() {
    return Collections.unmodifiableList(fighterList);
  }

  public boolean isInDungeon() {
    return dungeonInformation.isInDungeon();
  }

  public void entersDungeon(Dungeon dungeon, int level) throws MyOwnException {
    checkIfTeamIsInDungeon();

    if (getAverageLevel() < dungeon.getDifficulty() * 5) {
      throw new MyOwnException(() -> "Durchschnittslevel nicht hoch genug.", null);
    }

    if (!isAlive()) {
      throw new MyOwnException(() -> "Alle Teammitglieder sind bewusstlos.", null);
    }

    dungeonInformation.entersDungeon(dungeon, level);
  }

  public void leavesDungeon() {
    dungeonInformation.leavesDungeon();
  }

  public void heal() throws MyOwnException {
    checkIfTeamIsInDungeon();

    for (Fighter fighter : this.fighterList) {
      fighter.heal();
    }
  }

  public Inventory getInventory() {
    return inventory;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void givePocketToPlayer() throws MyOwnException {
    checkIfTeamIsInDungeon();
    player.getInventory().addMoney(inventory.getMoney());
    player.getInventory().addStardust(inventory.getStardust());
    player.getInventory().addCookies(inventory.getCookies());
    player.getInventory().addMorphStones(inventory.getMorphStones());
    inventory.clear();
  }

  public boolean expand() throws MyOwnException {
    checkIfTeamIsInDungeon();
    if (this.teamSize < 6) {
      this.teamSize++;
      return true;
    }
    return false;
  }

  public int getTeamSize() {
    return teamSize;
  }

  public int getAverageLevel() {
    int levelSum = 0;
    for (Fighter fighter : this.fighterList) {
      levelSum = levelSum + fighter.getWaifu().getLevel();
    }

    if (levelSum == 0) {
      return 0;
    } else {
      return levelSum / this.fighterList.size();
    }
  }

  public int getHpPercentage() {
    int maxHp = this.fighterList.stream().mapToInt(fighter -> fighter.getWaifu().getHp()).sum();
    int currentHp = this.fighterList.stream().mapToInt(Fighter::getCurrentHp).sum();
    return maxHp == 0 ? 0 : currentHp * 100 / maxHp;
  }

  public boolean ownerIsMessaged() {
    return dungeonInformation.isOwnerMessaged();
  }

  public void setOwnerIsMessaged(boolean ownerIsMessaged) {
    dungeonInformation.setOwnerMessaged(ownerIsMessaged);
  }

  public List<List<FightHistory>> getFightHistories() {
    return fightHistories;
  }

  public Dungeon getCurrentDungeon() {
    return dungeonInformation.getCurrentDungeon();
  }

  public int getLevel() {
    return dungeonInformation.getLevel();
  }

  public void raiseLevel() {
    dungeonInformation.raiseLevel();
  }


  @Override
  public String getDisplayTitle() {
    return this.name;
  }

  @Override
  public String getDisplayBody() {

    StringBuilder body = new StringBuilder();
    if (this.getFighters().isEmpty()) {
      body.append("Leer");
    } else {
      body.append("Size: ").append(this.teamSize).append(" | HP: ").append(this.getHpPercentage())
          .append("%");
    }
    for (Fighter fighter : this.getFighters()) {
      body.append("\n").append(fighter.getWaifu().getName());
    }

    return body.toString();
  }

  @Override
  public String getDisplayImageUrl() {
    return null;
  }

  public void checkIfTeamIsInDungeon() throws MyOwnException {
    if (this.dungeonInformation.isInDungeon()) {
      throw new MyOwnException(new TeamIsInDungeon(this), null);
    }
  }

  public int getCurrentLevel() {
    return dungeonInformation.getLevel();
  }
}
