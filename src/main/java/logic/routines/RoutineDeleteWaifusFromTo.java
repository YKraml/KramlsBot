package logic.routines;

import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import domain.waifu.Waifu;
import logic.waifu.PlayerLoader;
import logic.waifu.WaifuLoader;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;
import domain.Answer;
import ui.messages.MessageSender;
import ui.messages.messages.WaifusDeleted;

import java.util.Collection;
import java.util.LinkedList;

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
