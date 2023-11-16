package logic.music.guess;

import com.google.inject.Inject;
import domain.GuessingGame;
import domain.exceptions.MyOwnException;
import domain.exceptions.messages.CouldNotGetGuessingGameByServer;
import logic.waifu.JikanFetcher;
import model.jikan.anime.animeByIdFull.AnimeFullById;
import model.jikan.anime.animeByIdFull.Data;
import model.jikan.anime.animeByIdFull.Entry;
import model.jikan.anime.animeByIdFull.Title;

import javax.inject.Singleton;
import java.util.*;
import java.util.stream.Collectors;

@Singleton
public final class GuessingGameManager {

    private final List<GuessingGame> guessingGameList;
    private final JikanFetcher jikanFetcher;

    @Inject
    public GuessingGameManager(JikanFetcher jikanFetcher) {
        this.jikanFetcher = jikanFetcher;
        this.guessingGameList = Collections.synchronizedList(new ArrayList<>());

    }

    public synchronized void startGuessingGame(AnimeFullById anime, String url, String songName, String serverId,
                                               int difficulty) {

        Collection<String> possibleAnswers = new HashSet<>();
        Data data = anime.getData();
        possibleAnswers.add(data.getTitle());
        possibleAnswers.add(data.getTitleEnglish());
        possibleAnswers.add(data.getTitleJapanese());
        possibleAnswers.addAll(data.getTitleSynonyms());
        possibleAnswers.addAll(data.getTitles().stream().map(Title::getTitle).collect(Collectors.toSet()));
        possibleAnswers.addAll(data.getRelations().stream().flatMap(relation -> relation.getEntry().stream()).map(Entry::getName).collect(Collectors.toSet()));
        possibleAnswers.removeIf(Objects::isNull);

        GuessingGame guessingGame = new GuessingGame(anime, url, songName, serverId, difficulty, possibleAnswers);
        this.guessingGameList.add(guessingGame);
    }

    public void removeGuessGame(String serverId) {
        guessingGameList.removeIf(game -> game.getServer().equals(serverId));
    }


    public GuessingGame getGuessingGameByServer(String serverId) throws MyOwnException {

        for (GuessingGame guessingGame : this.guessingGameList) {
            if (guessingGame.getServer().equals(serverId)) {
                return guessingGame;
            }
        }
        throw new MyOwnException(new CouldNotGetGuessingGameByServer(serverId), null);
    }

    public boolean makeGuess(String guess, String serverId) throws MyOwnException {

        List<String> guesses = new ArrayList<>();
        guesses.add(guess);
        guesses.addAll(jikanFetcher.searchForAnimeTitles(guess));

        GuessingGame guessingGame = getGuessingGameByServer(serverId);
        return guesses.stream().anyMatch(guessingGame::checkIfValidAnswer);

    }

    public boolean gameExists(String serverId) {
        for (GuessingGame guessingGame : this.guessingGameList) {
            if (guessingGame.getServer().equals(serverId)) {
                return true;
            }
        }
        return false;
    }


}
