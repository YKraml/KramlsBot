package routines;

import actions.listeners.commands.Answer;
import exceptions.MyOwnException;
import java.util.Optional;
import org.javacord.api.entity.channel.TextChannel;
import waifu.loader.PlayerLoader;
import waifu.model.Player;
import waifu.model.dungeon.Team;

public class RoutineExpandTeam extends Routine {

  private final Player player;
  private final String teamName;
  private final TextChannel channel;
  private final PlayerLoader playerLoader;

  public RoutineExpandTeam(Player player, String teamName, TextChannel channel,
      PlayerLoader playerLoader) {
    this.player = player;
    this.teamName = teamName;
    this.channel = channel;
    this.playerLoader = playerLoader;
  }

  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {
    Optional<Team> team = player.getTeamByName(teamName);
    if (team.isEmpty()) {
      channel.sendMessage(player.getNameTag() + ", konnte Team \"" + teamName + "\" nicht finden.");
      return new Answer("Someone tried to expand his team, but the team could not be found.");
    }


    int teamSize = team.get().getTeamSize();
    int cost = teamSize * 2000;
    if (!player.getInventory().hasMoney(cost)) {
      channel.sendMessage(
          player.getNameTag() + ", du hast nicht genug Geld. Du hast nur " + player.getInventory().getMoney() + " Euro und brauchst aber " + cost
              + " Euro");
      return new Answer("Someone tried to expand his team, but the player had not enough money.");
    }

    boolean expanded = team.get().expand();
    if (expanded) {
      Team team1 = team.get();
      channel.sendMessage(
          player.getNameTag() + ", das Team \"" + team1.getName() + "\" hat nun Platz fuer " + team1.getTeamSize() + " Waifus.");
      player.getInventory().removeMoney(cost);
      playerLoader.savePlayer(player);
    } else {
      channel.sendMessage(player.getNameTag() + ", das Team \"" + team.get().getName() + "\" hat schon seine Maximalgroesse erreicht.");
    }


    return new Answer("Someone expanded a team.");
  }
}
