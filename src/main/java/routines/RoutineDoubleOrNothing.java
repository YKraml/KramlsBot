package routines;

import actions.commands.Answer;
import embeds.LostMoney;
import exceptions.MyOwnException;
import messages.MessageSender;
import messages.MessageSenderImpl;
import messages.messages.WonMoney;
import org.javacord.api.entity.channel.TextChannel;
import waifu.loader.PlayerLoader;
import waifu.model.Player;

public class RoutineDoubleOrNothing extends Routine {

  private final Player player;
  private final TextChannel channel;
  private final PlayerLoader playerLoader;
  private final MessageSender messageSender;

  public RoutineDoubleOrNothing(Player player, TextChannel channel, PlayerLoader playerLoader,
      MessageSender messageSender) {
    this.player = player;
    this.channel = channel;
    this.playerLoader = playerLoader;
    this.messageSender = messageSender;
  }

  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {

    double r = Math.random();
    long wonMoney = player.getInventory().getMoney();

    if (r < 0.5) {
      player.getInventory().addMoney(Math.toIntExact(wonMoney));
      messageSender.send(new WonMoney(player, (int) wonMoney), channel);
    } else {
      player.getInventory().removeMoney(Math.toIntExact(wonMoney));
      MessageSenderImpl result;
      synchronized (MessageSenderImpl.class) {
        result = new MessageSenderImpl();
      }
      result.send(new LostMoney(player, wonMoney), channel);
    }

    playerLoader.savePlayer(player);

    return new Answer("Doppel oder Nichts ohne Parameter");
  }
}
