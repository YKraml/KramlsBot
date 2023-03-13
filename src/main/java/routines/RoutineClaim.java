package routines;

import actions.listeners.commands.Answer;
import exceptions.MyOwnException;
import exceptions.messages.ReachedMaxWaifus;
import java.util.Optional;
import messages.MessageSender;
import messages.messages.GuessedRight;
import messages.messages.GuessedWrong;
import messages.messages.WaifuStats;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import waifu.JikanFetcher;
import waifu.WaifuSpawnManager;
import waifu.loader.PlayerLoader;
import waifu.loader.WaifuLoader;
import waifu.model.Player;
import waifu.model.Waifu;

public class RoutineClaim extends Routine {

  private final Server server;
  private final TextChannel channel;
  private final User user;
  private final String guess;
  private final WaifuSpawnManager waifuSpawnManager;
  private final PlayerLoader playerLoader;
  private final WaifuLoader waifuLoader;
  private final JikanFetcher jikanFetcher;
  private final MessageSender messageSender;

  public RoutineClaim(Server server, TextChannel channel, User user, String guess,
      WaifuSpawnManager waifuSpawnManager, PlayerLoader playerLoader, WaifuLoader waifuLoader,
      JikanFetcher jikanFetcher, MessageSender messageSender) {
    this.server = server;
    this.channel = channel;
    this.user = user;
    this.guess = guess;
    this.waifuSpawnManager = waifuSpawnManager;
    this.playerLoader = playerLoader;
    this.waifuLoader = waifuLoader;
    this.jikanFetcher = jikanFetcher;
    this.messageSender = messageSender;
  }

  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {
    Player player = playerLoader.getPlayerByUser(user);

    if (player.getMaxWaifus() <= player.getMaxWaifus()) {
      throw new MyOwnException(new ReachedMaxWaifus(player), null);
    }

    Optional<Waifu> waifu = waifuSpawnManager.guessWaifu(server.getIdAsString(), guess);
    if (waifu.isPresent()) {
      player.addWaifu(waifu.get());
      messageSender.send(new GuessedRight(player), channel);
      messageSender.send(new WaifuStats(waifu.get(), player, playerLoader,
          waifuLoader, jikanFetcher, messageSender), channel);
      playerLoader.savePlayer(player);
    } else {
      messageSender.send(new GuessedWrong(player), channel);
    }

    return new Answer("Someone tried to claim a Waifu");
  }

}
