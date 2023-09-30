package actions.listeners.reaction;

import com.google.inject.Inject;
import java.util.List;
import model.jikan.anime.animeByIdFull.AnimeFullById;
import routines.RoutineAddToQueueBuilder;
import routines.RoutineRunner;
import waifu.JikanFetcher;

public class AnimeStartSongReactionListenerBuilder {

  private final JikanFetcher jikanFetcher;
  private final RoutineAddToQueueBuilder routineBuilder;
  private final RoutineRunner routineRunner;

  @Inject
  public AnimeStartSongReactionListenerBuilder(JikanFetcher jikanFetcher,
      RoutineAddToQueueBuilder routineBuilder, RoutineRunner routineRunner) {
    this.jikanFetcher = jikanFetcher;
    this.routineBuilder = routineBuilder;
    this.routineRunner = routineRunner;
  }


  public AnimeStartSongReactionListener createAnimeStartSongReactionListener(AnimeFullById anime,
      List<String> songs, String type) {
    return new AnimeStartSongReactionListener(anime, songs, type, jikanFetcher, routineBuilder,
        routineRunner);
  }
}