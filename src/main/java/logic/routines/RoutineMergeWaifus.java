package logic.routines;

import domain.Answer;
import domain.exceptions.MyOwnException;
import domain.exceptions.messages.CouldNotFindWaifu;
import domain.exceptions.messages.WaifuStarLevelAlreadyMax;
import domain.waifu.Player;
import domain.waifu.Stats;
import domain.waifu.Waifu;
import logic.waifu.PlayerLoader;
import logic.waifu.WaifuLoader;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;
import ui.messages.MessageSenderImpl;
import ui.messages.messages.Merged;
import ui.messages.messages.WaifusAreDifferent;

import java.util.List;

public class RoutineMergeWaifus extends Routine {

    private final User user;
    private final int waifuId2;
    private final int waifuId1;
    private final TextChannel channel;
    private final WaifuLoader waifuLoader;
    private final PlayerLoader playerLoader;

    public RoutineMergeWaifus(TextChannel channel, User user, int waifuId1, int waifuId2,
                              WaifuLoader waifuLoader, PlayerLoader playerLoader) {
        this.channel = channel;
        this.user = user;
        this.waifuId1 = waifuId1;
        this.waifuId2 = waifuId2;
        this.waifuLoader = waifuLoader;
        this.playerLoader = playerLoader;
    }

    @Override
    Answer start(RoutineRunner routineRunner) throws MyOwnException {
        Player player = playerLoader.getPlayerByUser(user);
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
            result.send(new WaifusAreDifferent(player), channel);
        }


        return new Answer("Someone merged Waifus.");
    }
}
