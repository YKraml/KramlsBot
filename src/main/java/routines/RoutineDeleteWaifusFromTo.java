package routines;

import actions.listeners.commands.Answer;
import exceptions.MyOwnException;
import java.util.Collection;
import java.util.LinkedList;
import messages.MessageSender;
import messages.messages.WaifusDeleted;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;
import waifu.loader.PlayerLoader;
import waifu.loader.WaifuLoader;
import waifu.model.Player;
import waifu.model.Waifu;

public class RoutineDeleteWaifusFromTo extends Routine {

  private final User user;
  private final int from;
  private final int to;
  private final WaifuLoader waifuLoader;
  private final PlayerLoader playerLoader;
  private final MessageSender messageSender;
  private final TextChannel channel;

  public RoutineDeleteWaifusFromTo(User user, int from, int to, WaifuLoader waifuLoader,
      PlayerLoader playerLoader, MessageSender messageSender, TextChannel channel) {
    this.user = user;
    this.from = from;
    this.to = to;
    this.waifuLoader = waifuLoader;
    this.playerLoader = playerLoader;
    this.messageSender = messageSender;
    this.channel = channel;
  }

  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {
    Player player = playerLoader.getPlayerByUser(user);
    int deletedWaifus = 0;
    int stardust = 0;
    int cookies = 0;
    Collection<Waifu> waifusToBeDeleted = new LinkedList<>();

    for (int i = 0; i < player.getWaifuList().size(); i++) {
      if (i >= from && i <= to) {
        Waifu waifu = player.getWaifuList().get(i);
        deletedWaifus++;
        cookies += waifu.getLevel();
        stardust += waifu.getRarity().getSellValue();
        waifusToBeDeleted.add(waifu);
      }
    }

    for (Waifu waifu : waifusToBeDeleted) {
      waifuLoader.deleteWaifu(waifu, player);
    }

    player.getInventory().addCookies(cookies);
    player.getInventory().addStardust(stardust);
    playerLoader.savePlayer(player);

    messageSender.send(new WaifusDeleted(player, deletedWaifus, stardust, cookies), channel);

    return new Answer("Someone deleted a Waifu");
  }
}
