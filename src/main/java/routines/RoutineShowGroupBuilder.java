package routines;

import messages.MessageSender;
import org.javacord.api.entity.channel.TextChannel;
import waifu.JikanFetcher;
import waifu.loader.PlayerLoader;
import waifu.loader.WaifuLoader;
import waifu.model.Player;

public class RoutineShowGroupBuilder {

  private final PlayerLoader playerLoader;
  private final WaifuLoader waifuLoader;
  private final JikanFetcher jikanFetcher;
  private final MessageSender messageSender;

  public RoutineShowGroupBuilder(PlayerLoader playerLoader, WaifuLoader waifuLoader,
      JikanFetcher jikanFetcher, MessageSender messageSender) {
    this.playerLoader = playerLoader;
    this.waifuLoader = waifuLoader;
    this.jikanFetcher = jikanFetcher;
    this.messageSender = messageSender;
  }

  public RoutineShowGroup createRoutineShowGroup(TextChannel channel, String groupName,
      Player player) {
    return new RoutineShowGroup(player, groupName, playerLoader, waifuLoader, jikanFetcher,
        messageSender, channel);
  }
}