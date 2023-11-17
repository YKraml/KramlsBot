package logic.routines;

import com.google.inject.Inject;
import logic.messages.MessageSender;
import logic.waifu.JikanFetcher;
import domain.PlayerLoader;
import domain.WaifuLoader;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;

public class RoutineShowWaifuListBuilder {

    private final MessageSender messageSender;
    private final PlayerLoader playerLoader;
    private final WaifuLoader waifuLoader;
    private final JikanFetcher jikanFetcher;

    @Inject
    public RoutineShowWaifuListBuilder(MessageSender messageSender, PlayerLoader playerLoader,
                                       WaifuLoader waifuLoader, JikanFetcher jikanFetcher) {
        this.messageSender = messageSender;
        this.playerLoader = playerLoader;
        this.waifuLoader = waifuLoader;
        this.jikanFetcher = jikanFetcher;
    }

    public RoutineShowWaifuList createRoutineShowWaifuList(User user, TextChannel channel) {
        return new RoutineShowWaifuList(messageSender, user, playerLoader, waifuLoader, jikanFetcher, channel);
    }
}