package routines;

import actions.listeners.commands.Answer;
import exceptions.MyOwnException;
import messages.MessageSender;
import messages.messages.GuessGameEndBuilder;
import messages.messages.GuessedRight;
import messages.messages.WonMoney;
import music.GuessingGame;
import music.GuessingGameManager;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import waifu.loader.PlayerLoader;
import waifu.model.Player;

public class RoutineGuess extends Routine {

  private final String guess;
  private final Server server;
  private final Player player;
  private final TextChannel channel;
  private final GuessingGameManager guessingGameManager;
  private final PlayerLoader playerLoader;
  private final MessageSender messageSender;
  private final GuessGameEndBuilder guessGameEndBuilder;

  public RoutineGuess(Server server, TextChannel channel, Player player, String guess,
      GuessingGameManager guessingGameManager, PlayerLoader playerLoader,
      MessageSender messageSender, GuessGameEndBuilder guessGameEndBuilder) {
    this.server = server;
    this.channel = channel;
    this.player = player;
    this.guess = guess;
    this.guessingGameManager = guessingGameManager;
    this.playerLoader = playerLoader;
    this.messageSender = messageSender;
    this.guessGameEndBuilder = guessGameEndBuilder;
  }

  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {
    boolean guessedRight = guessingGameManager.makeGuess(guess, server.getIdAsString());
    if (guessedRight) {

      GuessingGame guessingGame = guessingGameManager.getGuessingGameByServer(
          server.getIdAsString());
      guessingGameManager.removeGuessGame(server.getIdAsString());

      int wonMoney = 100 * guessingGame.getDifficulty();
      player.addToGuessesRight(1);
      player.getInventory().addMoney(wonMoney);
      playerLoader.savePlayer(player);

      messageSender.send(new GuessedRight(player), channel);
      messageSender.send(new WonMoney(player, wonMoney), channel);
      messageSender.send(guessGameEndBuilder.createGuessGameEnd(guessingGame), channel);

    } else {
      channel.sendMessage(player.getNameTag() + ", du lagst falsch!");
    }

    return new Answer("Someone tried to guess the Song.");
  }
}
