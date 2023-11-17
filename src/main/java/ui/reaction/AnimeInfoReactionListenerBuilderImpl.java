package ui.reaction;

import com.google.inject.Inject;
import logic.AnimeInfoReactionListenerBuilder;
import logic.messages.MessageSender;
import logic.waifu.JikanFetcher;
import model.jikan.anime.animeByIdFull.AnimeFullById;

public class AnimeInfoReactionListenerBuilderImpl implements AnimeInfoReactionListenerBuilder {

    private final JikanFetcher jikanFetcher;
    private final MessageSender messageSender;
    private final AnimeOpeningEndingReactionListenerBuilder animeOpeningEndingReactionListenerBuilder;

    @Inject
    public AnimeInfoReactionListenerBuilderImpl(JikanFetcher jikanFetcher,
                                            MessageSender messageSender,
                                            AnimeOpeningEndingReactionListenerBuilder animeOpeningEndingReactionListenerBuilder) {
        this.jikanFetcher = jikanFetcher;
        this.messageSender = messageSender;
        this.animeOpeningEndingReactionListenerBuilder = animeOpeningEndingReactionListenerBuilder;
    }


    @Override
    public AnimeInfoReactionListenerImpl createAnimeInfoReactionListener(AnimeFullById anime) {
        return new AnimeInfoReactionListenerImpl(anime, jikanFetcher, messageSender,
                animeOpeningEndingReactionListenerBuilder);
    }
}