package logic.routines;

import ui.commands.Answer;
import domain.exceptions.MyOwnException;
import ui.messages.MessageSender;
import ui.messages.messages.GuessGameEndBuilder;
import domain.GuessingGame;
import logic.music.guess.GuessingGameManager;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;

public class RoutineReveal extends Routine {

  private final TextChannel channel;
  private final Server server;
  private final GuessingGameManager guessingGameManager;
  private final MessageSender messageSender;
  private final GuessGameEndBuilder guessGameEndBuilder;

  public RoutineReveal(Server server, TextChannel channel, GuessingGameManager guessingGameManager, MessageSender messageSender, GuessGameEndBuilder guessGameEndBuilder) {
    this.server = server;
    this.channel = channel;
    this.guessingGameManager = guessingGameManager;
    this.messageSender = messageSender;
    this.guessGameEndBuilder = guessGameEndBuilder;
  }

  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {
    GuessingGame game = guessingGameManager.getGuessingGameByServer(server.getIdAsString());
    messageSender.send(guessGameEndBuilder.createGuessGameEnd(game), channel);
    guessingGameManager.removeGuessGame(server.getIdAsString());
    return new Answer("Guessing game was terminated");
  }
}
