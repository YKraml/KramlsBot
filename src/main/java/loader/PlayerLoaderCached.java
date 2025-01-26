package loader;

import com.google.inject.Inject;
import domain.PlayerLoader;
import domain.exceptions.MyOwnException;
import domain.exceptions.messages.CouldNotSavePlayer;
import domain.waifu.Player;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;
import javax.inject.Singleton;
import util.Terminal;

@Singleton
public class PlayerLoaderCached implements PlayerLoader {

  private final PlayerLoaderSql playerLoaderSql;
  private final Collection<Player> playerCache;
  private final ExecutorService executorService;
  private final Terminal terminal;

  @Inject
  public PlayerLoaderCached(PlayerLoaderSql playerLoaderSql, Terminal terminal) {
    this.playerLoaderSql = playerLoaderSql;
    this.terminal = terminal;
    playerCache = Collections.synchronizedList(new ArrayList<>());
    executorService = Executors.newFixedThreadPool(4);
  }

  @Override
  public void savePlayer(Player player) throws MyOwnException {

    Runnable runnable = () -> {
      try {
        playerLoaderSql.savePlayer(player);
      } catch (MyOwnException e) {
        throw new RuntimeException(e);
      }
    };

    BiConsumer<Void, Throwable> biConsumer = (unused, throwable) -> {
      if (throwable != null) {
        RuntimeException previousException = new RuntimeException(throwable);
        MyOwnException e = new MyOwnException(new CouldNotSavePlayer(player), previousException);
        terminal.printError(e);
        throwable.printStackTrace();
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
      Player player = playerLoaderSql.getPlayerById(userId);
      playerCache.add(player);
      return player;
    }
  }

}
