package logic.routines;

import domain.PlayerLoader;
import domain.WaifuLoader;
import domain.waifu.Player;
import logic.messages.MessageSender;
import logic.waifu.JikanFetcher;
import org.javacord.api.entity.channel.TextChannel;
import util.Terminal;

public class RoutineShowWaifuByIdBuilder {

  private final PlayerLoader playerLoader;
  private final WaifuLoader waifuLoader;
  private final JikanFetcher jikanFetcher;
  private final MessageSender messageSender;
  private final Terminal terminal;

  public RoutineShowWaifuByIdBuilder(PlayerLoader playerLoader, WaifuLoader waifuLoader,
      JikanFetcher jikanFetcher, MessageSender messageSender, Terminal terminal) {
    this.playerLoader = playerLoader;
    this.waifuLoader = waifuLoader;
    this.jikanFetcher = jikanFetcher;
    this.messageSender = messageSender;
    this.terminal = terminal;
  }

  public RoutineShowWaifuById createRoutineShowWaifuById(Player player, int index,
      TextChannel channel) {
    return new RoutineShowWaifuById(player, index, playerLoader, waifuLoader, jikanFetcher,
        messageSender, channel, terminal);
  }
}