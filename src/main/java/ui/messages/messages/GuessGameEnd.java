package ui.messages.messages;

import ui.reaction.AnimeInfoReactionListenerBuilder;
import ui.embeds.guess.GuessEndEmbed;
import domain.exceptions.MyOwnException;
import ui.messages.MyMessage;
import util.Emojis;
import domain.GuessingGame;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class GuessGameEnd extends MyMessage {

  private final GuessingGame game;
  private final AnimeInfoReactionListenerBuilder animeInfoReactionListenerBuilder;

  public GuessGameEnd(GuessingGame game,
      AnimeInfoReactionListenerBuilder animeInfoReactionListenerBuilder) {
    this.game = game;
    this.animeInfoReactionListenerBuilder = animeInfoReactionListenerBuilder;
  }

  @Override
  protected void startRoutine(Message message) throws MyOwnException {
    message.addReaction(Emojis.INFORMATION_SOURCE.getEmoji());
    message.addReactionAddListener(
        animeInfoReactionListenerBuilder.createAnimeInfoReactionListener(game.getAnime()));
  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
    return new GuessEndEmbed(game.getAnime(), game.getSongName(), game.getUrl(),
        game.getPossibleAnswers());
  }


}
