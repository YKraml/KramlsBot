package ui.messages.messages;

import domain.GuessingGame;
import domain.exceptions.MyOwnException;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.embeds.guess.GuessEndEmbed;
import ui.messages.MyMessage;
import ui.reaction.AnimeInfoReactionListenerBuilder;
import util.Emojis;

public class GuessGameEnd extends MyMessage {

    private final GuessingGame game;
    private final AnimeInfoReactionListenerBuilder animeInfoReactionListenerBuilder;

    public GuessGameEnd(GuessingGame game, AnimeInfoReactionListenerBuilder animeInfoReactionListenerBuilder) {
        this.game = game;
        this.animeInfoReactionListenerBuilder = animeInfoReactionListenerBuilder;
    }

    @Override
    protected void startRoutine(Message message) throws MyOwnException {
        message.addReaction(Emojis.INFORMATION_SOURCE.getEmoji());
        message.addReactionAddListener(animeInfoReactionListenerBuilder.createAnimeInfoReactionListener(game.getAnime()));
    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        return new GuessEndEmbed(game.getAnime(), game.getSongName(), game.getUrl(), game.getPossibleAnswers());
    }


}
