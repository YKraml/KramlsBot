package logic.routines;

import domain.Answer;
import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import logic.messages.MessageSender;
import domain.PlayerLoader;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;

import java.util.concurrent.ThreadLocalRandom;

public class RoutineGamblingDoubleWithParameter extends Routine {

    private final User user;
    private final long bettedMoney;
    private final TextChannel channel;
    private final PlayerLoader playerLoader;
    private final MessageSender messageSender;

    public RoutineGamblingDoubleWithParameter(User user, long betMoney, TextChannel channel,
                                              PlayerLoader playerLoader, MessageSender messageSender) {
        this.user = user;
        this.bettedMoney = betMoney;
        this.channel = channel;
        this.playerLoader = playerLoader;
        this.messageSender = messageSender;
    }

    @Override
    Answer start(RoutineRunner routineRunner) throws MyOwnException {
        Player player = playerLoader.getPlayerByUser(user);
        player.getInventory().removeMoney(bettedMoney);

        if (ThreadLocalRandom.current().nextDouble() >= 0.51) {
            player.getInventory().addMoney(bettedMoney * 2L);
            messageSender.sendWonMoney(channel, player, bettedMoney);
        } else {
            messageSender.sendLostMoney(channel, player, bettedMoney);
        }

        playerLoader.savePlayer(player);

        return new Answer("DoppelOderNichts mit Parameter");
    }
}
