package logic.routines;

import domain.Answer;
import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import logic.messages.MessageSender;
import logic.waifu.JikanFetcher;
import domain.PlayerLoader;
import domain.WaifuLoader;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;

public class RoutineShowGroupList extends Routine {

    private final MessageSender messageSender;
    private final User user;
    private final PlayerLoader playerLoader;
    private final WaifuLoader waifuLoader;
    private final JikanFetcher jikanFetcher;
    private final TextChannel channel;

    public RoutineShowGroupList(MessageSender messageSender, User user, PlayerLoader playerLoader,
                                WaifuLoader waifuLoader, JikanFetcher jikanFetcher, TextChannel channel) {
        this.messageSender = messageSender;
        this.user = user;
        this.playerLoader = playerLoader;
        this.waifuLoader = waifuLoader;
        this.jikanFetcher = jikanFetcher;
        this.channel = channel;
    }

    @Override
    Answer start(RoutineRunner routineRunner) throws MyOwnException {
        Player player = playerLoader.getPlayerByUser(user);
        messageSender.sendGroupList(channel, player, playerLoader, waifuLoader, jikanFetcher, messageSender);

        return new Answer("Showed someone his list.");
    }
}
