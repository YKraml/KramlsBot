package routines;

import messages.MessageSender;
import org.javacord.api.entity.channel.TextChannel;
import waifu.JikanFetcher;
import waifu.loader.PlayerLoader;
import waifu.loader.WaifuLoader;
import waifu.model.Player;

public class RoutineShowWaifuByIdBuilder {

  private final PlayerLoader playerLoader;
  private final WaifuLoader waifuLoader;
  private final JikanFetcher jikanFetcher;
  private final MessageSender messageSender;

  public RoutineShowWaifuByIdBuilder(PlayerLoader playerLoader, WaifuLoader waifuLoader,
      JikanFetcher jikanFetcher, MessageSender messageSender) {
    this.playerLoader = playerLoader;
    this.waifuLoader = waifuLoader;
    this.jikanFetcher = jikanFetcher;
    this.messageSender = messageSender;
  }

  public RoutineShowWaifuById createRoutineShowWaifuById(Player player, int index,
      TextChannel channel) {
    return new RoutineShowWaifuById(player, index, playerLoader, waifuLoader, jikanFetcher,
        messageSender, channel);
  }
}