package music;

import java.util.Collection;
import java.util.Collections;
import model.jikan.anime.animeByIdFull.AnimeFullById;

public class GuessingGame {

  private final Collection<String> possibleAnswers;
  private final String songName;
  private final AnimeFullById anime;
  private final String url;
  private final String serverId;
  private final int difficulty;

  public GuessingGame(AnimeFullById anime, String url, String songName, String serverId,
      int difficulty, Collection<String> possibleAnswers) {
    this.songName = songName;
    this.url = url;
    this.anime = anime;
    this.difficulty = difficulty;
    this.serverId = serverId;
    this.possibleAnswers = Collections.unmodifiableCollection(possibleAnswers);
  }


  public boolean checkIfValidAnswer(String guess) {
    return this.possibleAnswers.stream()
        .anyMatch(possibleAnswer -> possibleAnswer.equalsIgnoreCase(guess));
  }

  public Collection<String> getPossibleAnswers() {
    return possibleAnswers;
  }

  public String getSongName() {
    return songName;
  }

  public AnimeFullById getAnime() {
    return anime;
  }

  public String getUrl() {
    return url;
  }

  public String getServer() {
    return serverId;
  }

  public int getDifficulty() {
    return difficulty;
  }
}
