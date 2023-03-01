package routines;

import messages.MessageSender;
import org.javacord.api.entity.channel.TextChannel;
import waifu.JikanFetcher;
import waifu.WaifuBuilder;
import waifu.loader.PlayerLoader;
import waifu.loader.WaifuLoader;
import waifu.model.Player;

public class RoutineSpawnWaifuCommandBuilder {

  private final WaifuBuilder waifuBuilder;
  private final PlayerLoader playerLoader;
  private final WaifuLoader waifuLoader;
  private final JikanFetcher jikanFetcher;
  private final MessageSender messageSender;

  public RoutineSpawnWaifuCommandBuilder(WaifuBuilder waifuBuilder, PlayerLoader playerLoader,
      WaifuLoader waifuLoader, JikanFetcher jikanFetcher, MessageSender messageSender) {
    this.waifuBuilder = waifuBuilder;
    this.playerLoader = playerLoader;
    this.waifuLoader = waifuLoader;
    this.jikanFetcher = jikanFetcher;
    this.messageSender = messageSender;
  }


  public RoutineSpawnWaifuCommand createRoutineSpawnWaifuCommand(TextChannel channel, Player player) {
    return new RoutineSpawnWaifuCommand(channel, player, waifuBuilder, playerLoader, waifuLoader,
        jikanFetcher, messageSender);
  }
}