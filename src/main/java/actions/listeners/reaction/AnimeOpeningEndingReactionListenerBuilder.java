package actions.listeners.reaction;

import com.google.inject.Inject;
import messages.MessageSender;
import messages.messages.AnimeEndingsBuilder;
import messages.messages.AnimeOpeningsBuilder;
import model.jikan.anime.animeByIdFull.AnimeFullById;
import waifu.JikanFetcher;

public class AnimeOpeningEndingReactionListenerBuilder {

  private final JikanFetcher jikanFetcher;
  private final MessageSender messageSender;
  private final AnimeOpeningsBuilder animeOpeningsBuilder;
  private final AnimeEndingsBuilder animeEndingsBuilder;

  @Inject
  public AnimeOpeningEndingReactionListenerBuilder(JikanFetcher jikanFetcher,
      MessageSender messageSender,
      AnimeOpeningsBuilder animeOpeningsBuilder, AnimeEndingsBuilder animeEndingsBuilder) {
    this.jikanFetcher = jikanFetcher;
    this.messageSender = messageSender;
    this.animeOpeningsBuilder = animeOpeningsBuilder;
    this.animeEndingsBuilder = animeEndingsBuilder;
  }

  public AnimeOpeningEndingReactionListener createAnimeOpeningEndingReactionListener(
      AnimeFullById anime) {
    return new AnimeOpeningEndingReactionListener(anime, jikanFetcher, messageSender,
        animeOpeningsBuilder, animeEndingsBuilder);
  }
}