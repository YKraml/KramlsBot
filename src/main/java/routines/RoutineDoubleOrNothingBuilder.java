package routines;

import messages.MessageSender;
import org.javacord.api.entity.channel.TextChannel;
import waifu.loader.PlayerLoader;
import waifu.model.Player;

public class RoutineDoubleOrNothingBuilder {

  private final PlayerLoader playerLoader;
  private final MessageSender messageSender;

  public RoutineDoubleOrNothingBuilder(PlayerLoader playerLoader, MessageSender messageSender) {
    this.playerLoader = playerLoader;
    this.messageSender = messageSender;
  }

  public RoutineDoubleOrNothing createRoutineDoubleOrNothing(Player player, TextChannel channel) {
    return new RoutineDoubleOrNothing(player, channel, playerLoader, messageSender);
  }
}