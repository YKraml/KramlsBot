package logic;

import model.jikan.anime.animeByIdFull.AnimeFullById;

public interface AnimeInfoReactionListenerBuilder {

  AnimeInfoReactionListener createAnimeInfoReactionListener(AnimeFullById anime);
}
