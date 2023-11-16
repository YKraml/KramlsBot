package domain.waifu.dungeon;

import com.google.inject.Inject;
import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import domain.waifu.Waifu;
import domain.waifu.fighting.FightHistory;
import logic.waifu.PlayerLoader;
import logic.waifu.TeamLoader;
import logic.waifu.WaifuBuilder;
import org.javacord.api.entity.channel.TextChannel;
import ui.messages.MessageSender;
import ui.messages.messages.TeamIsLow;
import ui.messages.messages.TeamKilled;
import util.ChannelFinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DungeonTicker {

    private final PlayerLoader playerLoader;
    private final ChannelFinder channelFinder;
    private final MessageSender messageSender;
    private final TeamLoader teamLoader;
    private final WaifuBuilder waifuBuilder;

    @Inject
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

            giveReward(team.getLevel(), team, dungeon.getDifficulty());
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

            Inventory inventory = team.getInventory().losePartialInventory();
            teamsToBeRemoved.add(team);

            Optional<TextChannel> textChannelOptional = channelFinder.getTextChannelById(
                    dungeon.getChannelId());
            if (textChannelOptional.isPresent()) {
                messageSender.send(new TeamKilled(team, level, inventory), textChannelOptional.get());
            }
        }
    }

    private void checkIfTeamIsLow(Team team, ChannelFinder channelFinder, Dungeon dungeon)
            throws MyOwnException {

        boolean teamIsLow = team.getHpPercentage() < 25;
        boolean ownerNotJetMessaged = !team.ownerIsMessaged();
        if (teamIsLow && ownerNotJetMessaged) {

            Optional<TextChannel> textChannelOptional = channelFinder.getTextChannelById(
                    dungeon.getChannelId());
            if (textChannelOptional.isPresent()) {
                textChannelOptional.get().sendMessage(team.getPlayer().getNameTag());
                messageSender.send(new TeamIsLow(team), textChannelOptional.get());
            }

            team.setOwnerIsMessaged(true);
        }
    }

    private void fightEnemies(Team team, int level, Dungeon dungeon) {
        Team enemyTeam = createEnemies(level, dungeon);
        List<FightHistory> fightHistoryList = team.fight(enemyTeam);
        team.addFightHistoryList(fightHistoryList);
    }

    private Team createEnemies(int dungeonLevel, Dungeon dungeon) {

        int numberOfEnemies = (int) ((Math.random() * dungeon.getDifficulty()) + 1);
        Player tempPlayer = new Player("DUNGEON", "DUNGEON");
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

    private void giveReward(int level, Team team, int difficulty) {
        int wonMoney = (int) (difficulty * (Math.random() * 20) + level);
        int wonStardust = (int) (difficulty * (Math.random() * 10) + level);
        int wonCookies = (int) (difficulty * 0.05 + Math.random());
        int wonMorphStones = Math.abs(level) % 100 == 99 && Math.random() < difficulty / 10d ? 1 : 0;

        team.getInventory().addMoney(wonMoney);
        team.getInventory().addStardust(wonStardust);
        team.getInventory().addCookies(wonCookies);
        team.getInventory().addMorphStones(wonMorphStones);
    }
}
