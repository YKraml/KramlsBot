package ui.messages.messages;

import ui.reaction.StartGuessingGameListener;
import util.Emojis;
import ui.embeds.guess.GuessStartEmbed;
import domain.exceptions.MyOwnException;
import ui.messages.MyMessage;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import logic.routines.RoutineRevealBuilder;
import logic.routines.RoutineRunner;

public class GuessGameStarted extends MyMessage {

  private final RoutineRunner routineRunner;
  private final RoutineRevealBuilder routineRevealBuilder;

  public GuessGameStarted(RoutineRunner routineRunner, RoutineRevealBuilder routineRevealBuilder) {
    this.routineRunner = routineRunner;
    this.routineRevealBuilder = routineRevealBuilder;
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
    return new GuessStartEmbed();
  }

}
