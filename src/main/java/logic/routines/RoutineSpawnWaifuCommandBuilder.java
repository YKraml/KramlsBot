package logic.routines;

import com.google.inject.Inject;
import domain.PlayerLoader;
import domain.WaifuLoader;
import logic.messages.MessageSender;
import logic.waifu.JikanFetcher;
import logic.waifu.WaifuBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;
import util.Terminal;

public class RoutineSpawnWaifuCommandBuilder {

  private final WaifuBuilder waifuBuilder;
  private final PlayerLoader playerLoader;
  private final WaifuLoader waifuLoader;
  private final JikanFetcher jikanFetcher;
  private final MessageSender messageSender;
  private final Terminal terminal;

  @Inject
  public RoutineSpawnWaifuCommandBuilder(WaifuBuilder waifuBuilder, PlayerLoader playerLoader,
      WaifuLoader waifuLoader, JikanFetcher jikanFetcher, MessageSender messageSender,
      Terminal terminal) {
    this.waifuBuilder = waifuBuilder;
    this.playerLoader = playerLoader;
    this.waifuLoader = waifuLoader;
    this.jikanFetcher = jikanFetcher;
    this.messageSender = messageSender;
    this.terminal = terminal;
  }


  public RoutineSpawnWaifuCommand createRoutineSpawnWaifuCommand(TextChannel channel, User user) {
    return new RoutineSpawnWaifuCommand(channel, user, waifuBuilder, playerLoader, waifuLoader,
        jikanFetcher, messageSender, terminal);
  }
}