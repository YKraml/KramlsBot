package routines;

import com.google.inject.Inject;
import messages.MessageSender;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import waifu.JikanFetcher;
import waifu.WaifuSpawnManager;
import waifu.loader.PlayerLoader;
import waifu.loader.WaifuLoader;

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


  public RoutineClaim createRoutineClaim(Server server, TextChannel channel, User user,
      String guess) {
    return new RoutineClaim(server, channel, user, guess, waifuSpawnManager, playerLoader,
        waifuLoader, jikanFetcher, messageSender);
  }
}