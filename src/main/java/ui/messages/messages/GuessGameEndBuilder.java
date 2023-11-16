package ui.messages.messages;

import com.google.inject.Inject;
import domain.GuessingGame;
import ui.reaction.AnimeInfoReactionListenerBuilder;

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