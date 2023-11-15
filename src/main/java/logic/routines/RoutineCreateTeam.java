package logic.routines;

import ui.commands.Answer;
import domain.exceptions.MyOwnException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;
import logic.waifu.PlayerLoader;
import domain.waifu.Player;
import domain.waifu.dungeon.Inventory;
import domain.waifu.dungeon.Team;

public class RoutineCreateTeam extends Routine {

  private final User user;
  private final String teamName;
  private final TextChannel channel;
  private final PlayerLoader playerLoader;

  public RoutineCreateTeam(TextChannel channel, User player, String teamName,
      PlayerLoader playerLoader) {
    this.channel = channel;
    this.user = player;
    this.teamName = teamName;
    this.playerLoader = playerLoader;
  }

  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {
    Player player = playerLoader.getPlayerByUser(user);
    if (player.getTeamByName(teamName).isPresent()) {
      channel.sendMessage(player.getNameTag() + ", Team \"" + teamName + "\" existiert bereits.");
      return new Answer("Someone wanted to create a Team, but the name is alredy used");
    }

    String id = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSS").format(LocalDateTime.now());
    Team team = new Team(id, teamName, player, 1, new Inventory());

    int ownedTeams = player.getTeamList().size();
    int costPerTeam = 10000;
    int cost = ownedTeams * costPerTeam;

    player.getInventory().removeMoney(cost);
    player.addTeam(team);
    channel.sendMessage(team.getPlayer().getNameTag() + ", Team \"" + team.getName() + "\" erstellt.");

    playerLoader.savePlayer(player);
    return new Answer("Someone created a Team");
  }
}
