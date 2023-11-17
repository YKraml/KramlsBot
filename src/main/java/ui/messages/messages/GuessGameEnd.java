package ui.messages.messages;

import domain.GuessingGame;
import domain.exceptions.MyOwnException;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.embeds.guess.GuessEndEmbed;
import ui.messages.MyMessageAbs;
import logic.AnimeInfoReactionListenerBuilder;
import domain.Emojis;

public class GuessGameEnd extends MyMessageAbs {

    private final GuessingGame game;
    private final AnimeInfoReactionListenerBuilder animeInfoReactionListenerBuilder;

    public GuessGameEnd(GuessingGame game, AnimeInfoReactionListenerBuilder animeInfoReactionListenerBuilder) {
        this.game = game;
        this.animeInfoReactionListenerBuilder = animeInfoReactionListenerBuilder;
    }

    @Override
    public void startRoutine(Message message) throws MyOwnException {
        message.addReaction(Emojis.INFORMATION_SOURCE.getEmoji());
        message.addReactionAddListener(animeInfoReactionListenerBuilder.createAnimeInfoReactionListener(game.getAnime()));
    }

    @Override
    public EmbedBuilder getContent() throws MyOwnException {
        return new GuessEndEmbed(game.getAnime(), game.getSongName(), game.getUrl(), game.getPossibleAnswers());
    }


}
