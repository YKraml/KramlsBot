package routines;

import com.google.inject.Inject;
import messages.MessageSender;
import org.javacord.api.entity.channel.TextChannel;
import waifu.JikanFetcher;
import waifu.loader.PlayerLoader;
import waifu.loader.WaifuLoader;
import waifu.model.Player;

public class RoutineShowGroupListBuilder {

  private final MessageSender messageSender;
  private final PlayerLoader playerLoader;
  private final WaifuLoader waifuLoader;
  private final JikanFetcher jikanFetcher;

  @Inject
  public RoutineShowGroupListBuilder(MessageSender messageSender, PlayerLoader playerLoader,
      WaifuLoader waifuLoader, JikanFetcher jikanFetcher) {
    this.messageSender = messageSender;
    this.playerLoader = playerLoader;
    this.waifuLoader = waifuLoader;
    this.jikanFetcher = jikanFetcher;
  }

  public RoutineShowGroupList createRoutineShowGroupList(TextChannel channel, Player player) {
    return new RoutineShowGroupList(messageSender, player, playerLoader, waifuLoader, jikanFetcher,
        channel);
  }
}