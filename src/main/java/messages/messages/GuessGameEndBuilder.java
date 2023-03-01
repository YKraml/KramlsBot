package messages.messages;

import actions.listeners.reaction.AnimeInfoReactionListenerBuilder;
import music.GuessingGame;

public class GuessGameEndBuilder {

  private final AnimeInfoReactionListenerBuilder animeInfoReactionListenerBuilder;

  public GuessGameEndBuilder(AnimeInfoReactionListenerBuilder animeInfoReactionListenerBuilder) {
    this.animeInfoReactionListenerBuilder = animeInfoReactionListenerBuilder;
  }

  public GuessGameEnd createGuessGameEnd(GuessingGame game) {
    return new GuessGameEnd(game, animeInfoReactionListenerBuilder);
  }
}