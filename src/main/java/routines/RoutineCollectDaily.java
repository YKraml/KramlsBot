package routines;

import actions.commands.Answer;
import exceptions.MyOwnException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import messages.MessageSenderImpl;
import messages.messages.DailyAlreadyUsed;
import messages.messages.DailyUsed;
import org.javacord.api.entity.channel.TextChannel;
import waifu.loader.PlayerLoader;
import waifu.model.Player;

public class RoutineCollectDaily extends Routine {

  private final Player player;
  private final TextChannel channel;
  private final PlayerLoader playerLoader;

  public RoutineCollectDaily(TextChannel channel, Player player, PlayerLoader playerLoader) {
    this.channel = channel;
    this.player = player;
    this.playerLoader = playerLoader;
  }

  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {
    String oldDate = player.getLastDaily();
    String newDate = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDateTime.now());

    boolean alreadyClaimed = oldDate.equals(newDate);
    if (alreadyClaimed) {
      MessageSenderImpl result;
      synchronized (MessageSenderImpl.class) {
        result = new MessageSenderImpl();
      }
      result.send(new DailyAlreadyUsed(player, newDate), channel);
    } else {

      MessageSenderImpl result;
      synchronized (MessageSenderImpl.class) {
        result = new MessageSenderImpl();
      }
      result.send(new DailyUsed(player), channel);
      player.setLastDaily(newDate);
      player.getInventory().addMoney(1000);
      player.getInventory().addStardust(100);
      player.getInventory().addCookies(1);

      playerLoader.savePlayer(player);
    }

    return new Answer("Someone used his daily.");
  }
}
