package logic;

import model.jikan.anime.animeByIdFull.AnimeFullById;
import ui.reaction.AnimeInfoReactionListenerImpl;

public interface AnimeInfoReactionListenerBuilder {

  AnimeInfoReactionListener createAnimeInfoReactionListener(AnimeFullById anime);
}
