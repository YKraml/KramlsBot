package logic.routines;

import com.google.inject.Inject;
import domain.PlayerLoader;
import domain.WaifuLoader;
import logic.messages.MessageSender;
import logic.waifu.JikanFetcher;
import logic.waifu.WaifuSpawnManager;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import util.Terminal;

public class RoutineClaimBuilder {

  private final WaifuSpawnManager waifuSpawnManager;
  private final PlayerLoader playerLoader;
  private final WaifuLoader waifuLoader;
  private final JikanFetcher jikanFetcher;
  private final MessageSender messageSender;
  private final Terminal terminal;

  @Inject
  public RoutineClaimBuilder(WaifuSpawnManager waifuSpawnManager, PlayerLoader playerLoader,
      WaifuLoader waifuLoader, JikanFetcher jikanFetcher, MessageSender messageSender,
      Terminal terminal) {
    this.waifuSpawnManager = waifuSpawnManager;
    this.playerLoader = playerLoader;
    this.waifuLoader = waifuLoader;
    this.jikanFetcher = jikanFetcher;
    this.messageSender = messageSender;
    this.terminal = terminal;
  }


  public RoutineClaim createRoutineClaim(Server server, TextChannel channel, User user,
      String guess) {
    return new RoutineClaim(server, channel, user, guess, waifuSpawnManager, playerLoader,
        waifuLoader, jikanFetcher, messageSender, terminal);
  }
}