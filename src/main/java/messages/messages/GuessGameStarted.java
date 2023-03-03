package messages.messages;

import actions.listeners.reaction.StartGuessingGameListener;
import discord.Emojis;
import embeds.guess.GuessStartEmbed;
import exceptions.MyOwnException;
import messages.MyMessage;
import music.guess.GuessingGame;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import routines.RoutineRevealBuilder;
import routines.RoutineRunner;

public class GuessGameStarted extends MyMessage {

  private final RoutineRunner routineRunner;
  private final RoutineRevealBuilder routineRevealBuilder;
  private final GuessingGame guessingGame;

  public GuessGameStarted(RoutineRunner routineRunner, RoutineRevealBuilder routineRevealBuilder,
      GuessingGame guessingGameManager) {
    this.routineRunner = routineRunner;
    this.routineRevealBuilder = routineRevealBuilder;
    this.guessingGame = guessingGameManager;
  }

  @Override
  protected void startRoutine(Message message) throws MyOwnException {
    String revealEmoji = Emojis.INFORMATION_SOURCE.getEmoji();
    message.addReaction(revealEmoji);
    message.addReactionAddListener(new StartGuessingGameListener(revealEmoji, routineRunner,
        routineRevealBuilder));
  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
    return new GuessStartEmbed(guessingGame);
  }

}
