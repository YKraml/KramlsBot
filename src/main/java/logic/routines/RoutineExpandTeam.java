package logic.routines;

import domain.Answer;
import domain.PlayerLoader;
import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import domain.waifu.dungeon.Team;
import java.util.Optional;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;

public class RoutineExpandTeam extends Routine {

  private final User user;
  private final String teamName;
  private final TextChannel channel;
  private final PlayerLoader playerLoader;

  public RoutineExpandTeam(User user, String teamName, TextChannel channel,
      PlayerLoader playerLoader) {
    this.user = user;
    this.teamName = teamName;
    this.channel = channel;
    this.playerLoader = playerLoader;
  }

  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {
    Player player = playerLoader.getPlayerByUser(user);
    Optional<Team> team = player.getTeamByName(teamName);
    if (team.isEmpty()) {
      channel.sendMessage(player.getNameTag() + ", konnte Team \"" + teamName + "\" nicht finden.");
      return new Answer("Someone tried to expand his team, but the team could not be found.");
    }

    int teamSize = team.get().getTeamSize();
    int cost = teamSize * 2000;
    if (!player.getInventory().hasMoney(cost)) {
      channel.sendMessage(
          player.getNameTag() + ", du hast nicht genug Geld. Du hast nur " + player.getInventory()
              .getMoney() + " Euro und brauchst aber " + cost
              + " Euro");
      return new Answer("Someone tried to expand his team, but the player had not enough money.");
    }

    boolean expanded = team.get().expand();
    if (expanded) {
      channel.sendMessage(
          "%s, das Team \"%s\" hat nun Platz fuer %d Waifus.".formatted(player.getNameTag(),
              team.get().getName(), team.get().getTeamSize()));
      player.getInventory().removeMoney(cost);
      playerLoader.savePlayer(player);
    } else {
      channel.sendMessage("%s, das Team \"%s\" hat schon seine Maximalgroesse erreicht.".formatted(
          player.getNameTag(), team.get().getName()));
    }

    return new Answer("Someone expanded a team.");
  }
}
