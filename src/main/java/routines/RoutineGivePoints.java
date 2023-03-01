package routines;

import actions.commands.Answer;
import de.kraml.Terminal;
import exceptions.MyOwnException;
import org.javacord.api.entity.server.Server;
import waifu.loader.PlayerLoader;
import waifu.model.Player;

public class RoutineGivePoints extends Routine {

  private final Server server;
  private final long connectedUserId;
  private final int moneyPerMinute;
  private final int passedTimeInMinutes;
  private final PlayerLoader playerLoader;

  public RoutineGivePoints(Server server, long connectedUserId, int moneyPerMinute,
      int passedTimeInMinutes, PlayerLoader playerLoader) {
    this.server = server;
    this.connectedUserId = connectedUserId;
    this.moneyPerMinute = moneyPerMinute;
    this.passedTimeInMinutes = passedTimeInMinutes;
    this.playerLoader = playerLoader;
  }

  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {
    Player player = playerLoader.getPlayerById(String.valueOf(connectedUserId));
    int addedMoney = moneyPerMinute * passedTimeInMinutes;
    player.getInventory().addMoney(addedMoney);
    player.addToTimeOnServer(server.getIdAsString(), passedTimeInMinutes);
    playerLoader.savePlayer(player);
    Terminal.printLine("Gave points to '" + player.getName() + "'");
    return new Answer("Gave points to '%s'".formatted(player.getName()));
  }
}
