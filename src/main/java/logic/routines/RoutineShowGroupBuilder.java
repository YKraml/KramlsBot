package logic.routines;

import domain.waifu.Player;
import logic.MessageSender;
import logic.waifu.JikanFetcher;
import logic.waifu.PlayerLoader;
import logic.waifu.WaifuLoader;
import org.javacord.api.entity.channel.TextChannel;

public class RoutineShowGroupBuilder {

    private final PlayerLoader playerLoader;
    private final WaifuLoader waifuLoader;
    private final JikanFetcher jikanFetcher;
    private final MessageSender messageSender;

    public RoutineShowGroupBuilder(PlayerLoader playerLoader, WaifuLoader waifuLoader,
                                   JikanFetcher jikanFetcher, MessageSender messageSender) {
        this.playerLoader = playerLoader;
        this.waifuLoader = waifuLoader;
        this.jikanFetcher = jikanFetcher;
        this.messageSender = messageSender;
    }

    public RoutineShowGroup createRoutineShowGroup(TextChannel channel, String groupName,
                                                   Player player) {
        return new RoutineShowGroup(player, groupName, playerLoader, waifuLoader, jikanFetcher,
                messageSender, channel);
    }
}