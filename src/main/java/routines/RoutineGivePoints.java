package routines;

import actions.listeners.commands.Answer;
import com.google.inject.Inject;
import de.kraml.Terminal;
import exceptions.MyOwnException;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.server.Server;
import waifu.loader.PlayerLoader;
import waifu.model.Player;

public class RoutineGivePoints extends Routine {

  private final int moneyPerMinute;
  private final int passedTimeInMinutes;
  private final PlayerLoader playerLoader;
  private final DiscordApi api;

  @Inject
  public RoutineGivePoints(int moneyPerMinute, int passedTimeInMinutes, PlayerLoader playerLoader,
      DiscordApi api) {
    this.moneyPerMinute = moneyPerMinute;
    this.passedTimeInMinutes = passedTimeInMinutes;
    this.playerLoader = playerLoader;
    this.api = api;
  }

  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {

    for (Server server : api.getServers()) {
      for (ServerVoiceChannel voiceChannel : server.getVoiceChannels()) {
        for (Long userId : voiceChannel.getConnectedUserIds()) {
          Player player = playerLoader.getPlayerById(String.valueOf(userId));
          int addedMoney = moneyPerMinute * passedTimeInMinutes;
          player.getInventory().addMoney(addedMoney);
          player.addToTimeOnServer(server.getIdAsString(), passedTimeInMinutes);
          playerLoader.savePlayer(player);
          Terminal.printLine("Gave points to '" + player.getName() + "'");
        }
      }
    }

    return new Answer("Gave points to all Users");
  }
}
