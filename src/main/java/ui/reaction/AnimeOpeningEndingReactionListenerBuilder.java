package ui.reaction;

import com.google.inject.Inject;
import logic.messages.MessageSender;
import logic.waifu.JikanFetcher;
import model.jikan.anime.animeByIdFull.AnimeFullById;
import ui.messages.messages.AnimeEndingsBuilder;
import ui.messages.messages.AnimeOpeningsBuilder;

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