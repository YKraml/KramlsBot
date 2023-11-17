package logic;

import model.jikan.anime.animeByIdFull.AnimeFullById;
import ui.reaction.AnimeInfoReactionListener;

public interface AnimeInfoReactionListenerBuilder {

  AnimeInfoReactionListener createAnimeInfoReactionListener(AnimeFullById anime);
}
