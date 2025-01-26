package logic.routines;

import domain.Answer;
import domain.PlayerLoader;
import domain.WaifuLoader;
import domain.exceptions.MyOwnException;
import domain.exceptions.messages.NotEnoughResource;
import domain.waifu.Player;
import domain.waifu.Waifu;
import logic.messages.MessageSender;
import logic.waifu.JikanFetcher;
import logic.waifu.WaifuBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;
import util.Terminal;

public class RoutineSpawnWaifuCommand extends Routine {

  private final TextChannel channel;
  private final User user;
  private final WaifuBuilder waifuBuilder;
  private final PlayerLoader playerLoader;
  private final WaifuLoader waifuLoader;
  private final JikanFetcher jikanFetcher;
  private final MessageSender messageSender;
  private final Terminal terminal;

  public RoutineSpawnWaifuCommand(TextChannel channel, User user, WaifuBuilder waifuBuilder,
      PlayerLoader playerLoader, WaifuLoader waifuLoader, JikanFetcher jikanFetcher,
      MessageSender messageSender, Terminal terminal) {
    this.channel = channel;
    this.user = user;
    this.waifuBuilder = waifuBuilder;
    this.playerLoader = playerLoader;
    this.waifuLoader = waifuLoader;
    this.jikanFetcher = jikanFetcher;
    this.messageSender = messageSender;
    this.terminal = terminal;
  }

  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {
    Player player = playerLoader.getPlayerByUser(user);
    if (player.getInventory().getMoney() < 1000) {
      throw new MyOwnException(
          new NotEnoughResource(player.getInventory().getMoney(), 1000, "Geld"), null);
    }

    Waifu waifu = waifuBuilder.createRandomWaifu();
    player.addWaifu(waifu);

    player.getInventory().removeMoney(1000);

    messageSender.sendWaifuSpawned(channel, player);
    messageSender.sendWaifuStats(channel, waifu, player, playerLoader, waifuLoader, jikanFetcher,
        messageSender, terminal);
    playerLoader.savePlayer(player);

    return new Answer("Spawned a Waifu.");
  }
}
