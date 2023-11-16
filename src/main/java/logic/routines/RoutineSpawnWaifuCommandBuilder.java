package logic.routines;

import com.google.inject.Inject;
import logic.waifu.JikanFetcher;
import logic.waifu.PlayerLoader;
import logic.waifu.WaifuBuilder;
import logic.waifu.WaifuLoader;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;
import ui.messages.MessageSender;

public class RoutineSpawnWaifuCommandBuilder {

    private final WaifuBuilder waifuBuilder;
    private final PlayerLoader playerLoader;
    private final WaifuLoader waifuLoader;
    private final JikanFetcher jikanFetcher;
    private final MessageSender messageSender;

    @Inject
    public RoutineSpawnWaifuCommandBuilder(WaifuBuilder waifuBuilder, PlayerLoader playerLoader,
                                           WaifuLoader waifuLoader, JikanFetcher jikanFetcher, MessageSender messageSender) {
        this.waifuBuilder = waifuBuilder;
        this.playerLoader = playerLoader;
        this.waifuLoader = waifuLoader;
        this.jikanFetcher = jikanFetcher;
        this.messageSender = messageSender;
    }


    public RoutineSpawnWaifuCommand createRoutineSpawnWaifuCommand(TextChannel channel, User user) {
        return new RoutineSpawnWaifuCommand(channel, user, waifuBuilder, playerLoader, waifuLoader,
                jikanFetcher, messageSender);
    }
}