package routines;

import actions.commands.Answer;
import exceptions.MyOwnException;
import java.util.Optional;
import messages.MessageSender;
import messages.MessageSenderImpl;
import messages.messages.GuessedRight;
import messages.messages.GuessedWrong;
import messages.messages.WaifuStats;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import waifu.JikanFetcher;
import waifu.WaifuSpawnManager;
import waifu.loader.PlayerLoader;
import waifu.loader.WaifuLoader;
import waifu.model.Player;
import waifu.model.Waifu;

public class RoutineClaim extends Routine {

  private final Server server;
  private final TextChannel channel;
  private final Player player;
  private final String guess;
  private final WaifuSpawnManager waifuSpawnManager;
  private final PlayerLoader playerLoader;
  private final WaifuLoader waifuLoader;
  private final JikanFetcher jikanFetcher;
  private final MessageSender messageSender;

  public RoutineClaim(Server server, TextChannel channel, Player player, String guess,
      WaifuSpawnManager waifuSpawnManager, PlayerLoader playerLoader, WaifuLoader waifuLoader,
      JikanFetcher jikanFetcher, MessageSender messageSender) {
    this.server = server;
    this.channel = channel;
    this.player = player;
    this.guess = guess;
    this.waifuSpawnManager = waifuSpawnManager;
    this.playerLoader = playerLoader;
    this.waifuLoader = waifuLoader;
    this.jikanFetcher = jikanFetcher;
    this.messageSender = messageSender;
  }

  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {
    return doSomething(server,channel,player,guess);
  }

  private Answer doSomething(Server server, TextChannel channel, Player player, String guess)
      throws MyOwnException {
    Optional<Waifu> waifu = waifuSpawnManager.guessWaifu(server.getIdAsString(), guess);
    if(waifu.isPresent()){
      player.addWaifu(waifu.get());
      MessageSenderImpl result1;
      synchronized (MessageSenderImpl.class) {
        result1 = new MessageSenderImpl();
      }
      result1.send(new GuessedRight(player), channel);
      MessageSenderImpl result;
      synchronized (MessageSenderImpl.class) {
        result = new MessageSenderImpl();
      }
      result.send(new WaifuStats(waifu.get(), player, playerLoader,
          waifuLoader, jikanFetcher, messageSender), channel);
      playerLoader.savePlayer(player);
    }else {
      MessageSenderImpl result;
      synchronized (MessageSenderImpl.class) {
        result = new MessageSenderImpl();
      }
      result.send(new GuessedWrong(player), channel);
    }

    return new Answer("Someone tried to claim a Waifu");
  }
}
