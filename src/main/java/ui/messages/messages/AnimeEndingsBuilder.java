package ui.messages.messages;

import com.google.inject.Inject;
import model.jikan.anime.animeByIdFull.AnimeFullById;
import model.jikan.anime.animeThemes.AnimeThemes;
import ui.reaction.AnimeStartSongReactionListenerBuilder;

public class AnimeEndingsBuilder {

  private final AnimeStartSongReactionListenerBuilder animeStartSongReactionListenerBuilder;

  @Inject
  public AnimeEndingsBuilder(
      AnimeStartSongReactionListenerBuilder animeStartSongReactionListenerBuilder) {
    this.animeStartSongReactionListenerBuilder = animeStartSongReactionListenerBuilder;
  }

  public AnimeEndings createAnimeEndings(AnimeFullById anime, AnimeThemes animeThemes) {
    return new AnimeEndings(anime, animeThemes,
        animeStartSongReactionListenerBuilder);
  }
}