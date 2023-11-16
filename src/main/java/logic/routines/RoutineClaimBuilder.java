package logic.routines;

import com.google.inject.Inject;
import logic.MessageSender;
import logic.waifu.JikanFetcher;
import logic.waifu.PlayerLoader;
import logic.waifu.WaifuLoader;
import logic.waifu.WaifuSpawnManager;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class RoutineClaimBuilder {

    private final WaifuSpawnManager waifuSpawnManager;
    private final PlayerLoader playerLoader;
    private final WaifuLoader waifuLoader;
    private final JikanFetcher jikanFetcher;
    private final MessageSender messageSender;

    @Inject
    public RoutineClaimBuilder(WaifuSpawnManager waifuSpawnManager, PlayerLoader playerLoader,
                               WaifuLoader waifuLoader, JikanFetcher jikanFetcher, MessageSender messageSender) {
        this.waifuSpawnManager = waifuSpawnManager;
        this.playerLoader = playerLoader;
        this.waifuLoader = waifuLoader;
        this.jikanFetcher = jikanFetcher;
        this.messageSender = messageSender;
    }


    public RoutineClaim createRoutineClaim(Server server, TextChannel channel, User user,
                                           String guess) {
        return new RoutineClaim(server, channel, user, guess, waifuSpawnManager, playerLoader,
                waifuLoader, jikanFetcher, messageSender);
    }
}