package logic.routines;

import com.google.inject.Inject;
import logic.messages.MessageSender;
import logic.waifu.JikanFetcher;
import domain.PlayerLoader;
import logic.waifu.WaifuBuilder;
import domain.WaifuLoader;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;

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