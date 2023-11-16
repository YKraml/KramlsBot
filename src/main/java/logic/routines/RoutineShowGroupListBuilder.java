package logic.routines;

import com.google.inject.Inject;
import logic.waifu.JikanFetcher;
import logic.waifu.PlayerLoader;
import logic.waifu.WaifuLoader;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;
import ui.messages.MessageSender;

public class RoutineShowGroupListBuilder {

    private final MessageSender messageSender;
    private final PlayerLoader playerLoader;
    private final WaifuLoader waifuLoader;
    private final JikanFetcher jikanFetcher;

    @Inject
    public RoutineShowGroupListBuilder(MessageSender messageSender, PlayerLoader playerLoader,
                                       WaifuLoader waifuLoader, JikanFetcher jikanFetcher) {
        this.messageSender = messageSender;
        this.playerLoader = playerLoader;
        this.waifuLoader = waifuLoader;
        this.jikanFetcher = jikanFetcher;
    }

    public RoutineShowGroupList createRoutineShowGroupList(TextChannel channel, User user) {
        return new RoutineShowGroupList(messageSender, user, playerLoader, waifuLoader, jikanFetcher,
                channel);
    }
}