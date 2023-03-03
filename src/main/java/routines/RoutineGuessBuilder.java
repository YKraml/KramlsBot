package routines;

import com.google.inject.Inject;
import messages.MessageSender;
import messages.messages.GuessGameEndBuilder;
import music.guess.GuessingGameManager;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import waifu.loader.PlayerLoader;

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


  public RoutineGuess createRoutineGuess(Server server, TextChannel channel, User user, String guess) {
    return new RoutineGuess(server, channel, user, guess, guessingGameManager, playerLoader,
        messageSender, guessGameEndBuilder);
  }
}