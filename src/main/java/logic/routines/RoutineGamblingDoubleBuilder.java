package logic.routines;

import com.google.inject.Inject;
import logic.waifu.PlayerLoader;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;
import logic.MessageSender;

public class RoutineGamblingDoubleBuilder {


    private final PlayerLoader playerLoader;
    private final MessageSender messageSender;

    @Inject
    public RoutineGamblingDoubleBuilder(PlayerLoader playerLoader, MessageSender messageSender) {
        this.playerLoader = playerLoader;
        this.messageSender = messageSender;
    }

    public RoutineGamblingDoubleWithParameter createRoutineDoubleOrNothingWithParameter(
            User user, long betMoney, TextChannel channel) {
        return new RoutineGamblingDoubleWithParameter(user, betMoney, channel, playerLoader,
                messageSender);
    }
}