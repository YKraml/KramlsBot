package logic.routines;

import domain.waifu.Player;
import logic.messages.MessageSender;
import logic.waifu.JikanFetcher;
import domain.PlayerLoader;
import domain.WaifuLoader;
import org.javacord.api.entity.channel.TextChannel;

public class RoutineShowWaifuByIdBuilder {

    private final PlayerLoader playerLoader;
    private final WaifuLoader waifuLoader;
    private final JikanFetcher jikanFetcher;
    private final MessageSender messageSender;

    public RoutineShowWaifuByIdBuilder(PlayerLoader playerLoader, WaifuLoader waifuLoader,
                                       JikanFetcher jikanFetcher, MessageSender messageSender) {
        this.playerLoader = playerLoader;
        this.waifuLoader = waifuLoader;
        this.jikanFetcher = jikanFetcher;
        this.messageSender = messageSender;
    }

    public RoutineShowWaifuById createRoutineShowWaifuById(Player player, int index,
                                                           TextChannel channel) {
        return new RoutineShowWaifuById(player, index, playerLoader, waifuLoader, jikanFetcher,
                messageSender, channel);
    }
}