package logic.waifu;

import com.google.inject.Inject;
import domain.GuessingGame;
import domain.exceptions.MyOwnException;
import domain.exceptions.messages.CouldNotGetGuessingGameByServer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.inject.Singleton;
import model.jikan.anime.animeByIdFull.AnimeFullById;
import model.jikan.anime.animeByIdFull.Data;
import model.jikan.anime.animeByIdFull.Entry;
import model.jikan.anime.animeByIdFull.Title;

@Singleton
public final class GuessingGameManagerImpl implements GuessingGameManager {

  private final List<GuessingGame> guessingGameList;
  private final JikanFetcher jikanFetcher;

  @Inject
  public GuessingGameManagerImpl(JikanFetcher jikanFetcher) {
    this.jikanFetcher = jikanFetcher;
    this.guessingGameList = Collections.synchronizedList(new ArrayList<>());

  }

  @Override
  public synchronized void startGuessingGame(AnimeFullById anime, String url, String songName,
      String serverId,
      int difficulty) {

    Collection<String> possibleAnswers = new HashSet<>();
    Data data = anime.getData();
    possibleAnswers.add(data.getTitle());
    possibleAnswers.add(data.getTitleEnglish());
    possibleAnswers.add(data.getTitleJapanese());
    possibleAnswers.addAll(data.getTitleSynonyms());
    possibleAnswers.addAll(
        data.getTitles().stream().map(Title::getTitle).collect(Collectors.toSet()));
    possibleAnswers.addAll(
        data.getRelations().stream().flatMap(relation -> relation.getEntry().stream())
            .map(Entry::getName).collect(Collectors.toSet()));
    possibleAnswers.removeIf(Objects::isNull);

    GuessingGame guessingGame = new GuessingGame(anime, url, songName, serverId, difficulty,
        possibleAnswers);
    this.guessingGameList.add(guessingGame);
  }

  @Override
  public void removeGuessGame(String serverId) {
    guessingGameList.removeIf(game -> game.getServer().equals(serverId));
  }


  @Override
  public GuessingGame getGuessingGameByServer(String serverId) throws MyOwnException {

    for (GuessingGame guessingGame : this.guessingGameList) {
      if (guessingGame.getServer().equals(serverId)) {
        return guessingGame;
      }
    }
    throw new MyOwnException(new CouldNotGetGuessingGameByServer(serverId), null);
  }

  @Override
  public boolean makeGuess(String guess, String serverId) throws MyOwnException {

    List<String> guesses = new ArrayList<>();
    guesses.add(guess);
    guesses.addAll(jikanFetcher.searchForAnimeTitles(guess));

    GuessingGame guessingGame = getGuessingGameByServer(serverId);
    return guesses.stream().anyMatch(guessingGame::checkIfValidAnswer);

  }

  @Override
  public boolean gameExists(String serverId) {
    for (GuessingGame guessingGame : this.guessingGameList) {
      if (guessingGame.getServer().equals(serverId)) {
        return true;
      }
    }
    return false;
  }


}
