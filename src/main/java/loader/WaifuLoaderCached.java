package loader;

import com.google.inject.Inject;
import domain.WaifuLoader;
import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import domain.waifu.Waifu;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.inject.Singleton;

@Singleton
public class WaifuLoaderCached implements WaifuLoader {

  private final WaifuLoaderSql waifuLoaderSql;
  private final List<Waifu> waifuCache;

  @Inject
  public WaifuLoaderCached(WaifuLoaderSql waifuLoaderSql) {
    this.waifuLoaderSql = waifuLoaderSql;
    waifuCache = Collections.synchronizedList(new ArrayList<>());
  }

  @Override
  public List<Waifu> getWaifusFromPlayer(Player player) throws MyOwnException {
    List<Waifu> waifus = waifuLoaderSql.getWaifusFromPlayer(player);
    waifuCache.addAll(waifus);
    return waifus;
  }

  @Override
  public Optional<Waifu> getWaifuById(String id) throws MyOwnException {

    synchronized (this.waifuCache) {
      for (Waifu waifu : this.waifuCache) {
        if (waifu.getId().equals(id)) {
          return Optional.of(waifu);
        }
      }
    }

    return waifuLoaderSql.getWaifuById(id);
  }

  @Override
  public void saveWaifu(Waifu waifu, Player player) throws MyOwnException {
    waifuLoaderSql.saveWaifu(waifu, player);
  }

  @Override
  public void deleteWaifu(Waifu waifu, Player player) throws MyOwnException {
    waifuCache.remove(waifu);
    waifuLoaderSql.deleteWaifu(waifu, player);
  }
}
