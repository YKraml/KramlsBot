package messages.messages;

import actions.listeners.reaction.AnimeInfoReactionListenerBuilder;
import com.google.inject.Inject;
import music.guess.GuessingGame;

public class GuessGameEndBuilder {

  private final AnimeInfoReactionListenerBuilder animeInfoReactionListenerBuilder;

  @Inject
  public GuessGameEndBuilder(AnimeInfoReactionListenerBuilder animeInfoReactionListenerBuilder) {
    this.animeInfoReactionListenerBuilder = animeInfoReactionListenerBuilder;
  }

  public GuessGameEnd createGuessGameEnd(GuessingGame game) {
    return new GuessGameEnd(game, animeInfoReactionListenerBuilder);
  }
}