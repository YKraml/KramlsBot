package logic.routines;

import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import logic.waifu.PlayerLoader;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;
import domain.Answer;
import ui.embeds.LostMoney;
import logic.MessageSender;
import ui.messages.messages.WonMoney;

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
            messageSender.send(new WonMoney(player, bettedMoney), channel);
        } else {
            messageSender.send(new LostMoney(player, bettedMoney), channel);
        }

        playerLoader.savePlayer(player);

        return new Answer("DoppelOderNichts mit Parameter");
    }
}
