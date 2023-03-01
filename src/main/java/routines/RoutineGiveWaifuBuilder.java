package routines;

import com.google.inject.Inject;
import messages.MessageSender;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;
import waifu.loader.PlayerLoader;

public class RoutineGiveWaifuBuilder {
  private final PlayerLoader playerLoader;
  private final MessageSender messageSender;

  @Inject
  public RoutineGiveWaifuBuilder(PlayerLoader playerLoader, MessageSender messageSender) {
    this.playerLoader = playerLoader;
    this.messageSender = messageSender;
  }

  public RoutineGiveWaifu createRoutineGiveWaifu(TextChannel channel, User senderUser, User receiverUser,
      int waifuNumber) {
    return new RoutineGiveWaifu(channel, senderUser, receiverUser, waifuNumber, playerLoader, messageSender);
  }
}