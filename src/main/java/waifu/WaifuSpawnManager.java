package waifu;

import exceptions.MyOwnException;
import exceptions.messages.NoWaifuToClaim;
import java.util.function.Predicate;
import model.jikan.characters.charactersSearch.CharactersSearch;
import model.jikan.characters.charactersSearch.Datum;
import waifu.model.Waifu;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public final class WaifuSpawnManager {

  private final Map<String, Waifu> serverWaifuMap;
  private final JikanFetcher jikanFetcher;

  public WaifuSpawnManager(JikanFetcher jikanFetcher) {
    this.jikanFetcher = jikanFetcher;
    serverWaifuMap = Collections.synchronizedMap(new LinkedHashMap<>());
  }

  public void setWaifuToGuess(String serverId, Waifu waifu) {
    this.serverWaifuMap.put(serverId, waifu);
  }

  public synchronized Optional<Waifu> guessWaifu(String serverId, String name) throws MyOwnException {

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

  private boolean isGuessRight(String serverId, String name) throws MyOwnException {
    Waifu waifu = serverWaifuMap.getOrDefault(serverId, null);

    if (waifu == null) {
      throw new MyOwnException(new NoWaifuToClaim(), null);
    } else if (name.equalsIgnoreCase(waifu.getName())) {
      return true;
    } else {
      CharactersSearch characterSearch = jikanFetcher.getCharacterSearch(name);
      Predicate<Datum> predicate = datum -> datum.getName().equalsIgnoreCase(waifu.getName());
      return characterSearch.getData().stream().limit(5).anyMatch(predicate);
    }
  }

}
