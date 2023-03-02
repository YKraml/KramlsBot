package routines;

import actions.listeners.commands.Answer;
import exceptions.MyOwnException;
import exceptions.messages.NotEnoughResource;
import messages.MessageSender;
import messages.messages.WaifuSpawned;
import messages.messages.WaifuStats;
import org.javacord.api.entity.channel.TextChannel;
import waifu.JikanFetcher;
import waifu.WaifuBuilder;
import waifu.loader.PlayerLoader;
import waifu.loader.WaifuLoader;
import waifu.model.Player;
import waifu.model.Waifu;

public class RoutineSpawnWaifuCommand extends Routine {

  private final TextChannel channel;
  private final Player player;
  private final WaifuBuilder waifuBuilder;
  private final PlayerLoader playerLoader;
  private final WaifuLoader waifuLoader;
  private final JikanFetcher jikanFetcher;
  private final MessageSender messageSender;

  public RoutineSpawnWaifuCommand(TextChannel channel, Player player, WaifuBuilder waifuBuilder,
      PlayerLoader playerLoader, WaifuLoader waifuLoader, JikanFetcher jikanFetcher,
      MessageSender messageSender) {
    super();
    this.channel = channel;
    this.player = player;
    this.waifuBuilder = waifuBuilder;
    this.playerLoader = playerLoader;
    this.waifuLoader = waifuLoader;
    this.jikanFetcher = jikanFetcher;
    this.messageSender = messageSender;
  }

  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {
    if (player.getInventory().getMoney() < 1000) {
      throw new MyOwnException(new NotEnoughResource(player.getInventory(), 1000, "Geld"), null);
    }

    Waifu waifu = waifuBuilder.createRandomWaifu();
    player.addWaifu(waifu);

    player.getInventory().removeMoney(1000);

    messageSender.send(new WaifuSpawned(player), channel);
    messageSender.send(new WaifuStats(waifu, player, playerLoader, waifuLoader, jikanFetcher, messageSender), channel);
    playerLoader.savePlayer(player);

    return new Answer("Spawned a Waifu.");
  }
}
