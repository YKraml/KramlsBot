package routines;

import actions.commands.Answer;
import exceptions.MyOwnException;
import messages.MessageSender;
import messages.messages.GaveWaifu;
import messages.messages.WaifuNotFound;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;
import waifu.loader.PlayerLoader;
import waifu.model.Player;
import waifu.model.Waifu;

public class RoutineGiveWaifu extends Routine {

  private final User receiverUser;
  private final User senderUser;
  private final int waifuNumber;
  private final TextChannel channel;
  private final PlayerLoader playerLoader;
  private final MessageSender messageSender;

  public RoutineGiveWaifu(TextChannel channel, User senderUser, User receiverUser, int waifuNumber,
      PlayerLoader playerLoader, MessageSender messageSender) {
    this.channel = channel;
    this.senderUser = senderUser;
    this.receiverUser = receiverUser;
    this.waifuNumber = waifuNumber;
    this.playerLoader = playerLoader;
    this.messageSender = messageSender;
  }

  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {

    Player senderPlayer = playerLoader.getPlayerByUser(senderUser);
    Player receiverPlayer = playerLoader.getPlayerByUser(receiverUser);
    if (waifuNumber <= senderPlayer.getWaifuList().size()) {

      Waifu waifu = senderPlayer.getWaifuList().get(waifuNumber);
      receiverPlayer.addWaifu(waifu);
      senderPlayer.deleteWaifu(waifu);

      playerLoader.savePlayer(receiverPlayer);
      playerLoader.savePlayer(senderPlayer);

      messageSender.send(new GaveWaifu(senderPlayer, receiverPlayer, waifu), channel);
    } else {
      messageSender.send(new WaifuNotFound(waifuNumber), channel);
    }

    return new Answer("Someone gave a Waifu away to '%s'".formatted(receiverUser));
  }

}
