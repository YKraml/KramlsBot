package logic.waifu;

import com.google.inject.Inject;
import domain.exceptions.MyOwnException;
import domain.exceptions.messages.NoWaifuToClaim;
import domain.waifu.Waifu;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import javax.inject.Singleton;
import model.jikan.characters.charactersSearch.CharactersSearch;
import model.jikan.characters.charactersSearch.Datum;

@Singleton
public final class WaifuSpawnManager {

  private final Map<String, Waifu> serverWaifuMap;
  private final JikanFetcher jikanFetcher;

  @Inject
  public WaifuSpawnManager(JikanFetcher jikanFetcher) {
    this.jikanFetcher = jikanFetcher;
    serverWaifuMap = Collections.synchronizedMap(new LinkedHashMap<>());
  }

  public void setWaifuToGuess(String serverId, Waifu waifu) {
    this.serverWaifuMap.put(serverId, waifu);
  }

  public synchronized Optional<Waifu> guessWaifu(String serverId, String name)
      throws MyOwnException {

    if (isGuessRight(serverId, name)) {
      Optional<Waifu> waifuOptional = getWaifu(serverId);
      serverWaifuMap.remove(serverId);
      return waifuOptional;
    } else {
      return Optional.empty();
    }
  }

  public Optional<Waifu> getWaifu(String serverId) {
    return Optional.ofNullable(serverWaifuMap.getOrDefault(serverId, null));
  }

  public boolean isGuessRight(String serverId, String name) throws MyOwnException {

    if (!serverWaifuMap.containsKey(serverId)) {
      throw new MyOwnException(new NoWaifuToClaim(), null);
    }

    Waifu waifu = serverWaifuMap.get(serverId);
    if (name.equalsIgnoreCase(waifu.getName())) {
      return true;
    } else {
      CharactersSearch characterSearch = jikanFetcher.getCharacterSearch(name);
      Predicate<Datum> predicate = datum -> datum.getName().equalsIgnoreCase(waifu.getName());
      return characterSearch.getData().stream().limit(5).anyMatch(predicate);
    }
  }

}
