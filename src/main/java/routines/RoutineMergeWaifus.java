package routines;

import actions.commands.Answer;
import exceptions.MyOwnException;
import exceptions.messages.CouldNotFindWaifu;
import exceptions.messages.WaifuStarLevelAlreadyMax;
import java.util.List;
import messages.MessageSenderImpl;
import messages.messages.Merged;
import messages.messages.WaifusAreDiffernt;
import org.javacord.api.entity.channel.TextChannel;
import waifu.loader.PlayerLoader;
import waifu.loader.WaifuLoader;
import waifu.model.Player;
import waifu.model.Stats;
import waifu.model.Waifu;

public class RoutineMergeWaifus extends Routine {

  private final Player player;
  private final int waifuId2;
  private final int waifuId1;
  private final TextChannel channel;
  private final WaifuLoader waifuLoader;
  private final PlayerLoader playerLoader;

  public RoutineMergeWaifus(TextChannel channel, Player player, int waifuId1, int waifuId2,
      WaifuLoader waifuLoader, PlayerLoader playerLoader) {
    this.channel = channel;
    this.player = player;
    this.waifuId1 = waifuId1;
    this.waifuId2 = waifuId2;
    this.waifuLoader = waifuLoader;
    this.playerLoader = playerLoader;
  }

  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {
    List<Waifu> waifuList = player.getWaifuList();
    if (waifuList.size() <= waifuId2) {
      throw new MyOwnException(new CouldNotFindWaifu(waifuId2), null);
    } else if (waifuList.size() <= waifuId1) {
      throw new MyOwnException(new CouldNotFindWaifu(waifuId1), null);
    }

    Waifu waifu1 = waifuList.get(waifuId1);
    Waifu waifu2 = waifuList.get(waifuId2);

    if (waifu1.getStarLevel() >= Stats.MAX_STAR_LEVEL) {
      throw new MyOwnException(new WaifuStarLevelAlreadyMax(waifu1), null);
    }


    if (waifu1.getIdMal().equals(waifu2.getIdMal())) {
      waifuLoader.deleteWaifu(waifu2, player);
      waifu1.raiseStarLevelBy(waifu2.getStarLevel() + 1);
      MessageSenderImpl result;
      synchronized (MessageSenderImpl.class) {
        result = new MessageSenderImpl();
      }
      result.send(new Merged(player, waifu1), channel);
      playerLoader.savePlayer(player);
    } else {
      MessageSenderImpl result;
      synchronized (MessageSenderImpl.class) {
        result = new MessageSenderImpl();
      }
      result.send(new WaifusAreDiffernt(player), channel);
    }


    return new Answer("Someone merged Waifus.");
  }
}
