package logic.routines;

import domain.Answer;
import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import domain.waifu.Waifu;
import logic.messages.MessageSender;
import domain.PlayerLoader;
import domain.WaifuLoader;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class RoutineDeleteWaifusFromAnime extends Routine {

    private final WaifuLoader waifuLoader;
    private final PlayerLoader playerLoader;
    private final User user;
    private final String anime;
    private final TextChannel channel;
    private final MessageSender messageSender;

    public RoutineDeleteWaifusFromAnime(WaifuLoader waifuLoader, PlayerLoader playerLoader,
                                        User user, String anime, TextChannel channel, MessageSender messageSender) {
        this.waifuLoader = waifuLoader;
        this.playerLoader = playerLoader;
        this.user = user;
        this.anime = anime;
        this.channel = channel;
        this.messageSender = messageSender;
    }

    @Override
    Answer start(RoutineRunner routineRunner) throws MyOwnException {
        Player player = playerLoader.getPlayerByUser(user);
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

        messageSender.sendWaifusDeleted(channel, player, deletedWaifus, stardust, cookies);

        return new Answer("Someone deleted a Waifu");
    }
}
