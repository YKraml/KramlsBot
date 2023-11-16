package ui.reaction;

import com.google.inject.Inject;
import logic.routines.RoutineAddToQueueBuilder;
import logic.routines.RoutineRunner;
import logic.waifu.JikanFetcher;
import model.jikan.anime.animeByIdFull.AnimeFullById;

import java.util.List;

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