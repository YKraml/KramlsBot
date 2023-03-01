package routines;

import com.google.inject.Inject;
import messages.MessageSender;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import waifu.JikanFetcher;
import waifu.WaifuSpawnManager;
import waifu.loader.PlayerLoader;
import waifu.loader.WaifuLoader;
import waifu.model.Player;

public class RoutineClaimBuilder {

  private final WaifuSpawnManager waifuSpawnManager;
  private final PlayerLoader playerLoader;
  private final WaifuLoader waifuLoader;
  private final JikanFetcher jikanFetcher;
  private final MessageSender messageSender;

  @Inject
  public RoutineClaimBuilder(WaifuSpawnManager waifuSpawnManager, PlayerLoader playerLoader,
      WaifuLoader waifuLoader, JikanFetcher jikanFetcher, MessageSender messageSender) {
    this.waifuSpawnManager = waifuSpawnManager;
    this.playerLoader = playerLoader;
    this.waifuLoader = waifuLoader;
    this.jikanFetcher = jikanFetcher;
    this.messageSender = messageSender;
  }


  public RoutineClaim createRoutineClaim(Server server, TextChannel channel, Player player,
      String guess) {
    return new RoutineClaim(server, channel, player, guess, waifuSpawnManager, playerLoader,
        waifuLoader, jikanFetcher, messageSender);
  }
}