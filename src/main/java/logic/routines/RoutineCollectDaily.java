package logic.routines;

import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import logic.waifu.PlayerLoader;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;
import domain.Answer;
import ui.messages.MessageSenderImpl;
import ui.messages.messages.DailyAlreadyUsed;
import ui.messages.messages.DailyUsed;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RoutineCollectDaily extends Routine {

    private final User user;
    private final TextChannel channel;
    private final PlayerLoader playerLoader;

    public RoutineCollectDaily(TextChannel channel, User user, PlayerLoader playerLoader) {
        this.channel = channel;
        this.user = user;
        this.playerLoader = playerLoader;
    }

    @Override
    Answer start(RoutineRunner routineRunner) throws MyOwnException {
        Player player = playerLoader.getPlayerByUser(user);
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
