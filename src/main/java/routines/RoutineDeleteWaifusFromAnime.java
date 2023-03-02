package routines;

import actions.listeners.commands.Answer;
import exceptions.MyOwnException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import messages.MessageSender;
import messages.messages.WaifusDeleted;
import org.javacord.api.entity.channel.TextChannel;
import waifu.loader.PlayerLoader;
import waifu.loader.WaifuLoader;
import waifu.model.Player;
import waifu.model.Waifu;

public class RoutineDeleteWaifusFromAnime extends Routine {

  private final WaifuLoader waifuLoader;
  private final PlayerLoader playerLoader;
  private final Player player;
  private final String anime;
  private final TextChannel channel;
  private final MessageSender messageSender;

  public RoutineDeleteWaifusFromAnime(WaifuLoader waifuLoader, PlayerLoader playerLoader,
      Player player, String anime, TextChannel channel, MessageSender messageSender) {
    this.waifuLoader = waifuLoader;
    this.playerLoader = playerLoader;
    this.player = player;
    this.anime = anime;
    this.channel = channel;
    this.messageSender = messageSender;
  }

  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {
    List<Waifu> waifuList = new ArrayList<>(player.getWaifuList());

    int deletedWaifus = 0;
    int stardust = 0;
    int cookies = 0;
    Collection<Waifu> waifusToBeDeleted = new LinkedList<>();

    for (Waifu waifu : waifuList) {
      if (waifu.getAnimeName().equalsIgnoreCase(anime)) {
        deletedWaifus++;
        stardust += waifu.getRarity().getSellValue();
        cookies += waifu.getLevel();
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
