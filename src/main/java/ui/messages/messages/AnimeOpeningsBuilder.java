package ui.messages.messages;

import ui.reaction.AnimeStartSongReactionListenerBuilder;
import com.google.inject.Inject;
import model.jikan.anime.animeByIdFull.AnimeFullById;
import model.jikan.anime.animeThemes.AnimeThemes;

public class AnimeOpeningsBuilder {

  private final AnimeStartSongReactionListenerBuilder animeStartSongReactionListenerBuilder;

  @Inject
  public AnimeOpeningsBuilder(
      AnimeStartSongReactionListenerBuilder animeStartSongReactionListenerBuilder) {
    this.animeStartSongReactionListenerBuilder = animeStartSongReactionListenerBuilder;
  }


  public AnimeOpenings createAnimeOpenings(AnimeFullById anime, AnimeThemes animeThemes) {
    return new AnimeOpenings(anime, animeThemes, animeStartSongReactionListenerBuilder);
  }
}