package waifu.loader;

import de.kraml.Terminal;
import exceptions.MyOwnException;
import exceptions.messages.CouldNotSavePlayer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;
import waifu.model.Player;

public class PlayerLoaderCached implements PlayerLoader {

  private final PlayerLoader playerLoaderReal;
  private final Collection<Player> playerCache;
  private final ExecutorService executorService;

  public PlayerLoaderCached(TeamLoader teamLoader, WaifuLoader waifuLoader, GroupLoader groupLoader) {
    playerLoaderReal = new PlayerLoaderSql(teamLoader, waifuLoader, groupLoader);
    playerCache = Collections.synchronizedList(new ArrayList<>());
    executorService = Executors.newFixedThreadPool(4);
  }

  @Override
  public void savePlayer(Player player) throws MyOwnException {

    Runnable runnable = () -> {
      try {
        playerLoaderReal.savePlayer(player);
      } catch (MyOwnException e) {
        throw new RuntimeException(e);
      }
    };

    BiConsumer<Void, Throwable> biConsumer = (unused, throwable) -> {
      if (throwable != null) {
        RuntimeException previousException = new RuntimeException(throwable);
        MyOwnException e = new MyOwnException(new CouldNotSavePlayer(player), previousException);
        Terminal.printError(e);
      }
    };

    CompletableFuture<Void> future = CompletableFuture.runAsync(runnable, executorService);
    future.whenComplete(biConsumer);
  }

  @Override
  public Player getPlayerById(String userId) throws MyOwnException {
    synchronized (this) {
      for (Player player : this.playerCache) {
        if (player.getId().equals(userId)) {
          return player;
        }
      }
      Player player = playerLoaderReal.getPlayerById(userId);
      playerCache.add(player);
      return player;
    }
  }

}
