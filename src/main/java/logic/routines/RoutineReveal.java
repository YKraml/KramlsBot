package logic.routines;

import domain.Answer;
import domain.GuessingGame;
import domain.exceptions.MyOwnException;
import logic.AnimeInfoReactionListenerBuilder;
import logic.messages.MessageSender;
import logic.waifu.GuessingGameManager;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;

public class RoutineReveal extends Routine {

  private final TextChannel channel;
  private final Server server;
  private final GuessingGameManager guessingGameManager;
  private final MessageSender messageSender;
  private final AnimeInfoReactionListenerBuilder animeInfoReactionListenerBuilder;

  public RoutineReveal(Server server, TextChannel channel, GuessingGameManager guessingGameManager,
      MessageSender messageSender,
      AnimeInfoReactionListenerBuilder animeInfoReactionListenerBuilder) {
    this.server = server;
    this.channel = channel;
    this.guessingGameManager = guessingGameManager;
    this.messageSender = messageSender;
    this.animeInfoReactionListenerBuilder = animeInfoReactionListenerBuilder;
  }

  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {
    GuessingGame game = guessingGameManager.getGuessingGameByServer(server.getIdAsString());
    messageSender.sendGuessGameEnd(channel, game, animeInfoReactionListenerBuilder);
    guessingGameManager.removeGuessGame(server.getIdAsString());
    return new Answer("Guessing game was terminated");
  }
}
