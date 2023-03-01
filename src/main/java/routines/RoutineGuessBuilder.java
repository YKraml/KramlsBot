package routines;

import com.google.inject.Inject;
import messages.MessageSender;
import messages.messages.GuessGameEndBuilder;
import music.GuessingGameManager;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import waifu.loader.PlayerLoader;
import waifu.model.Player;

public class RoutineGuessBuilder {

  private final GuessingGameManager guessingGameManager;
  private final PlayerLoader playerLoader;
  private final MessageSender messageSender;
  private final GuessGameEndBuilder guessGameEndBuilder;

  @Inject
  public RoutineGuessBuilder(GuessingGameManager guessingGameManager, PlayerLoader playerLoader,
      MessageSender messageSender, GuessGameEndBuilder guessGameEndBuilder) {
    this.guessingGameManager = guessingGameManager;
    this.playerLoader = playerLoader;
    this.messageSender = messageSender;
    this.guessGameEndBuilder = guessGameEndBuilder;
  }


  public RoutineGuess createRoutineGuess(Server server, TextChannel channel, Player player, String guess) {
    return new RoutineGuess(server, channel, player, guess, guessingGameManager, playerLoader,
        messageSender, guessGameEndBuilder);
  }
}