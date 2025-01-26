package logic.routines;

import domain.Answer;
import domain.PlayerLoader;
import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import logic.messages.MessageSender;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;

public class RoutineGiveMoney extends Routine {

  private final PlayerLoader playerLoader;
  private final TextChannel channel;
  private final User giverUser;
  private final User receiverUser;
  private final int money;
  private final MessageSender messageSender;

  public RoutineGiveMoney(PlayerLoader playerLoader, TextChannel channel, User giverUser,
      User receiverUser, int money, MessageSender messageSender) {
    this.playerLoader = playerLoader;
    this.channel = channel;
    this.giverUser = giverUser;
    this.receiverUser = receiverUser;
    this.money = money;
    this.messageSender = messageSender;
  }

  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {
    Player giverPlayer = playerLoader.getPlayerByUser(giverUser);
    Player receiverPlayer = playerLoader.getPlayerByUser(receiverUser);

    giverPlayer.getInventory().removeMoney(money);
    receiverPlayer.getInventory().addMoney(money);

    playerLoader.savePlayer(giverPlayer);
    playerLoader.savePlayer(receiverPlayer);

    messageSender.sendGaveMoney(channel, giverPlayer, receiverPlayer, money);

    return new Answer("Someone gave someone Money.");
  }
}
