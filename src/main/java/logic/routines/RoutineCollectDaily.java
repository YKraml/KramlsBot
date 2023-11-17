package logic.routines;

import domain.Answer;
import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import logic.MessageSender;
import logic.waifu.PlayerLoader;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RoutineCollectDaily extends Routine {

    private final User user;
    private final TextChannel channel;
    private final PlayerLoader playerLoader;
    private final MessageSender messageSender;

    public RoutineCollectDaily(TextChannel channel, User user, PlayerLoader playerLoader, MessageSender messageSender) {
        this.channel = channel;
        this.user = user;
        this.playerLoader = playerLoader;
        this.messageSender = messageSender;
    }

    @Override
    Answer start(RoutineRunner routineRunner) throws MyOwnException {
        Player player = playerLoader.getPlayerByUser(user);
        String oldDate = player.getLastDaily();
        String newDate = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDateTime.now());

        boolean alreadyClaimed = oldDate.equals(newDate);
        if (alreadyClaimed) {
            messageSender.sendDailyAlreadyUsed(channel, player, newDate);
        } else {
            messageSender.sendDailyUsed(channel, player);
            player.setLastDaily(newDate);
            player.getInventory().addMoney(1000);
            player.getInventory().addStardust(100);
            player.getInventory().addCookies(1);

            playerLoader.savePlayer(player);
        }

        return new Answer("Someone used his daily.");
    }
}
