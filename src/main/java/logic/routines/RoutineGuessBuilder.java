package logic.routines;

import com.google.inject.Inject;
import domain.PlayerLoader;
import logic.AnimeInfoReactionListenerBuilder;
import logic.messages.MessageSender;
import logic.waifu.GuessingGameManager;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class RoutineGuessBuilder {

  private final GuessingGameManager guessingGameManager;
  private final PlayerLoader playerLoader;
  private final MessageSender messageSender;
  private final AnimeInfoReactionListenerBuilder animeInfoReactionListenerBuilder;

  @Inject
  public RoutineGuessBuilder(GuessingGameManager guessingGameManager, PlayerLoader playerLoader,
      MessageSender messageSender,
      AnimeInfoReactionListenerBuilder animeInfoReactionListenerBuilder) {
    this.guessingGameManager = guessingGameManager;
    this.playerLoader = playerLoader;
    this.messageSender = messageSender;
    this.animeInfoReactionListenerBuilder = animeInfoReactionListenerBuilder;
  }


  public RoutineGuess createRoutineGuess(Server server, TextChannel channel, User user,
      String guess) {
    return new RoutineGuess(server, channel, user, guess, guessingGameManager, playerLoader,
        messageSender, animeInfoReactionListenerBuilder);
  }
}