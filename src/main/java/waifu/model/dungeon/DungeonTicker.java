package waifu.model.dungeon;

import de.kraml.Terminal;
import discord.ChannelFinder;
import exceptions.MyOwnException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import messages.MessageSender;
import messages.messages.TeamIsLow;
import messages.messages.TeamKilled;
import org.javacord.api.entity.channel.TextChannel;
import waifu.WaifuBuilder;
import waifu.loader.PlayerLoader;
import waifu.loader.TeamLoader;
import waifu.model.Player;
import waifu.model.Waifu;
import waifu.model.fighting.FightHistory;

public class DungeonTicker {

  private final PlayerLoader playerLoader;
  private final ChannelFinder channelFinder;
  private final MessageSender messageSender;
  private final TeamLoader teamLoader;
  private final WaifuBuilder waifuBuilder;

  public DungeonTicker(PlayerLoader playerLoader, ChannelFinder channelFinder,
      MessageSender messageSender, TeamLoader teamLoader, WaifuBuilder waifuBuilder) {
    this.playerLoader = playerLoader;
    this.channelFinder = channelFinder;
    this.messageSender = messageSender;
    this.teamLoader = teamLoader;
    this.waifuBuilder = waifuBuilder;
  }

  public void tick(Dungeon dungeon) throws MyOwnException {

    List<Team> teamsToBeRemoved = new ArrayList<>();

    List<Team> teams = teamLoader.getTeamsInDungeon(dungeon.getChannelId(), playerLoader);

    for (Team team : teams) {

      giveTeamReward(team.getLevel(), team, dungeon);
      team.raiseLevel();
      fightEnemies(team, team.getLevel(), dungeon);
      checkIfTeamIsLow(team, channelFinder, dungeon);
      checkIfTeamDied(teamsToBeRemoved, team, team.getLevel(), channelFinder, dungeon);

      playerLoader.savePlayer(team.getPlayer());
    }

    for (Team team : teamsToBeRemoved) {
      team.leavesDungeon();
    }

  }

  private void checkIfTeamDied(List<Team> teamsToBeRemoved, Team team, int level,
      ChannelFinder channelFinder, Dungeon dungeon) throws MyOwnException {
    if (!team.isAlive()) {
      Terminal.printLine(team.getFightHistories().toString());
      Inventory inventory = team.getInventory().losePartialInventory();
      teamsToBeRemoved.add(team);
      Optional<TextChannel> textChannelOptional = channelFinder.getTextChannelById(dungeon.getChannelId());
      if (textChannelOptional.isPresent()) {
        messageSender.send(new TeamKilled(team, level, inventory), textChannelOptional.get());
      }
    }
  }

  private void checkIfTeamIsLow(Team team, ChannelFinder channelFinder, Dungeon dungeon) throws MyOwnException {

    boolean teamIsLow = team.getHpPercentage() < 25;
    boolean ownerNotJetMessaged = !team.ownerIsMessaged();
    if (teamIsLow && ownerNotJetMessaged) {

      Optional<TextChannel> textChannelOptional = channelFinder.getTextChannelById(dungeon.getChannelId());
      if (textChannelOptional.isPresent()) {
        textChannelOptional.get().sendMessage(team.getPlayer().getNameTag());
        messageSender.send(new TeamIsLow(team), textChannelOptional.get());
      }

      team.setOwnerIsMessaged(true);
    }
  }

  private void fightEnemies(Team team, int level, Dungeon dungeon) throws MyOwnException {
    Team enemyTeam = createEnemies(level, dungeon);
    List<FightHistory> fightHistoryList = team.fight(enemyTeam);
    team.addFightHistoryList(fightHistoryList);
  }

  private Team createEnemies(int dungeonLevel, Dungeon dungeon) throws MyOwnException {

    int numberOfEnemies = (int) ((Math.random() * dungeon.getDifficulty()) + 1);
    Player tempPlayer = new Player("DUNGEON", "DUNGEON");
    DungeonInformation dungeonInformation = new DungeonInformation();
    Team team = new Team("TEMP", "TEMP", tempPlayer, numberOfEnemies, new Inventory());
    tempPlayer.addTeam(team);

    for (int i = 0; i < numberOfEnemies; i++) {

      try {
        int waifuLevel = (int) (dungeonLevel + Math.pow(dungeon.getDifficulty(), 2));
        Waifu waifu = waifuBuilder.createBatleWaifu(waifuLevel);
        team.addWaifuToTeam(waifu, waifu.getHp());
      } catch (Exception ignore) {
        //Ignore
      }

    }
    return team;
  }

  private void giveTeamReward(int level, Team team, Dungeon dungeon) {
    int wonMoney = (int) (dungeon.getDifficulty() * (Math.random() * 20) + level);
    int wonStardust = (int) (dungeon.getDifficulty() * (Math.random() * 10) + level);
    int wonCookies = (int) (dungeon.getDifficulty() * 0.05 + Math.random());

    team.getInventory().addMoney(wonMoney);
    team.getInventory().addStardust(wonStardust);
    team.getInventory().addCookies(wonCookies);
  }
}
