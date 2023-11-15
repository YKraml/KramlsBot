package logic.routines;

import ui.messages.MessageSender;
import org.javacord.api.entity.channel.TextChannel;
import logic.waifu.JikanFetcher;
import logic.waifu.PlayerLoader;
import logic.waifu.WaifuLoader;
import domain.waifu.Player;

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