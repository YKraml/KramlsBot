package ui.messages.messages;

import domain.exceptions.MyOwnException;
import logic.waifu.JikanFetcher;
import model.jikan.anime.animeByIdFull.AnimeFullById;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.embeds.anime.AnimeEmbed;
import logic.MessageSender;
import ui.messages.MyMessage;
import ui.reaction.AnimeOpeningEndingReactionListenerBuilder;
import ui.reaction.CharacterReactionListener;
import ui.reaction.SynopsisReactionListener;
import util.Emojis;

public class AnimeInformation extends MyMessage {

    private final AnimeFullById anime;
    private final JikanFetcher jikanFetcher;
    private final AnimeOpeningEndingReactionListenerBuilder animeOpeningEndingReactionListenerBuilder;
    private final MessageSender messageSender;

    public AnimeInformation(AnimeFullById anime, JikanFetcher jikanFetcher,
                            AnimeOpeningEndingReactionListenerBuilder animeOpeningEndingReactionListenerBuilder,
                            MessageSender messageSender) {
        this.anime = anime;
        this.jikanFetcher = jikanFetcher;
        this.animeOpeningEndingReactionListenerBuilder = animeOpeningEndingReactionListenerBuilder;
        this.messageSender = messageSender;
    }

    @Override
    protected void startRoutine(Message message) throws MyOwnException {
        message.addReaction(Emojis.MUSICAL_NOTE.getEmoji());
        message.addReaction(Emojis.NOTES.getEmoji());
        message.addReaction(Emojis.INFORMATION_SOURCE.getEmoji());
        message.addReaction(Emojis.MAGE.getEmoji());
        message.addReactionAddListener(
                animeOpeningEndingReactionListenerBuilder.createAnimeOpeningEndingReactionListener(anime));
        message.addReactionAddListener(new SynopsisReactionListener(anime));
        message.addReactionAddListener(new CharacterReactionListener(anime, jikanFetcher, messageSender));
    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        return new AnimeEmbed(anime);
    }


}
