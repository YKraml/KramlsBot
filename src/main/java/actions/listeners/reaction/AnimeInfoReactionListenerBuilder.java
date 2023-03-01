package actions.listeners.reaction;

import com.google.inject.Inject;
import messages.MessageSender;
import model.jikan.anime.animeByIdFull.AnimeFullById;
import waifu.JikanFetcher;

public class AnimeInfoReactionListenerBuilder {

  private final JikanFetcher jikanFetcher;
  private final MessageSender messageSender;
  private final AnimeOpeningEndingReactionListenerBuilder animeOpeningEndingReactionListenerBuilder;

  @Inject
  public AnimeInfoReactionListenerBuilder(JikanFetcher jikanFetcher,
      MessageSender messageSender,
      AnimeOpeningEndingReactionListenerBuilder animeOpeningEndingReactionListenerBuilder) {
    this.jikanFetcher = jikanFetcher;
    this.messageSender = messageSender;
    this.animeOpeningEndingReactionListenerBuilder = animeOpeningEndingReactionListenerBuilder;
  }


  public AnimeInfoReactionListener createAnimeInfoReactionListener(AnimeFullById anime) {
    return new AnimeInfoReactionListener(anime, jikanFetcher, messageSender,
        animeOpeningEndingReactionListenerBuilder);
  }
}