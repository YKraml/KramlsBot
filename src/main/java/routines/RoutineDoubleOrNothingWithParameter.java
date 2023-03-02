package routines;

import actions.listeners.commands.Answer;
import embeds.LostMoney;
import exceptions.MyOwnException;
import messages.MessageSenderImpl;
import messages.messages.WonMoney;
import org.javacord.api.entity.channel.TextChannel;
import waifu.loader.PlayerLoader;
import waifu.model.Player;

public class RoutineDoubleOrNothingWithParameter extends Routine {

  private final Player player;
  private final int bettedMoney;
  private final TextChannel channel;
  private final PlayerLoader playerLoader;

  public RoutineDoubleOrNothingWithParameter(Player player, int bettedMoney, TextChannel channel,
      PlayerLoader playerLoader) {
    this.player = player;
    this.bettedMoney = bettedMoney;
    this.channel = channel;
    this.playerLoader = playerLoader;
  }

  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {
    player.getInventory().removeMoney(bettedMoney);

    double r = Math.random();

    if (r >= 0.5) {
      player.getInventory().addMoney(bettedMoney * 2L);
      MessageSenderImpl result;
      synchronized (MessageSenderImpl.class) {
        result = new MessageSenderImpl();
      }
      result.send(new WonMoney(player, bettedMoney), channel);
    } else {
      MessageSenderImpl result;
      synchronized (MessageSenderImpl.class) {
        result = new MessageSenderImpl();
      }
      result.send(new LostMoney(player, bettedMoney), channel);
    }

    playerLoader.savePlayer(player);

    return new Answer("DoppelOderNichts mit Parameter");
  }
}
