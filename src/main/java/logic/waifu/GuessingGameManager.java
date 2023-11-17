package logic.waifu;

import domain.GuessingGame;
import domain.exceptions.MyOwnException;
import model.jikan.anime.animeByIdFull.AnimeFullById;

public interface GuessingGameManager {
    void startGuessingGame(AnimeFullById anime, String url, String songName, String serverId,
                           int difficulty);

    void removeGuessGame(String serverId);

    GuessingGame getGuessingGameByServer(String serverId) throws MyOwnException;

    boolean makeGuess(String guess, String serverId) throws MyOwnException;

    boolean gameExists(String serverId);
}
