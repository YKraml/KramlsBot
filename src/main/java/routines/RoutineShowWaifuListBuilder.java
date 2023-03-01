package routines;

import messages.MessageSender;
import org.javacord.api.entity.channel.TextChannel;
import waifu.JikanFetcher;
import waifu.loader.PlayerLoader;
import waifu.loader.WaifuLoader;
import waifu.model.Player;

public class RoutineShowWaifuListBuilder {

  private final MessageSender messageSender;
  private final PlayerLoader playerLoader;
  private final WaifuLoader waifuLoader;
  private final JikanFetcher jikanFetcher;

  public RoutineShowWaifuListBuilder(MessageSender messageSender, PlayerLoader playerLoader,
      WaifuLoader waifuLoader, JikanFetcher jikanFetcher) {
    this.messageSender = messageSender;
    this.playerLoader = playerLoader;
    this.waifuLoader = waifuLoader;
    this.jikanFetcher = jikanFetcher;
  }

  public RoutineShowWaifuList createRoutineShowWaifuList(Player player, TextChannel channel) {
    return new RoutineShowWaifuList(messageSender, player, playerLoader, waifuLoader, jikanFetcher, channel);
  }
}