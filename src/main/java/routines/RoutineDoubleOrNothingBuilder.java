package routines;

import com.google.inject.Inject;
import messages.MessageSender;
import org.javacord.api.entity.channel.TextChannel;
import waifu.loader.PlayerLoader;
import waifu.model.Player;

public class RoutineDoubleOrNothingBuilder {

  private final PlayerLoader playerLoader;
  private final MessageSender messageSender;

  @Inject
  public RoutineDoubleOrNothingBuilder(PlayerLoader playerLoader, MessageSender messageSender) {
    this.playerLoader = playerLoader;
    this.messageSender = messageSender;
  }

  public RoutineDoubleOrNothing createRoutineDoubleOrNothing(Player player, TextChannel channel) {
    return new RoutineDoubleOrNothing(player, channel, playerLoader, messageSender);
  }
}