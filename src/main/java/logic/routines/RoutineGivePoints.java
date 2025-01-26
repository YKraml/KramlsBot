package logic.routines;

import domain.Answer;
import domain.PlayerLoader;
import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.server.Server;
import util.Terminal;

public class RoutineGivePoints extends Routine {

  private final int moneyPerMinute;
  private final int passedTimeInMinutes;
  private final PlayerLoader playerLoader;
  private final DiscordApi api;
  private final Terminal terminal;


  public RoutineGivePoints(int moneyPerMinute, int passedTimeInMinutes, PlayerLoader playerLoader,
      DiscordApi api, Terminal terminal) {
    this.moneyPerMinute = moneyPerMinute;
    this.passedTimeInMinutes = passedTimeInMinutes;
    this.playerLoader = playerLoader;
    this.api = api;
    this.terminal = terminal;
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
          terminal.printLine("Gave points to '%s'".formatted(player.getName()));
        }
      }
    }

    return new Answer("Gave points to all Users");
  }
}
