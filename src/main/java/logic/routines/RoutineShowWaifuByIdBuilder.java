package logic.routines;

import domain.waifu.Player;
import logic.waifu.JikanFetcher;
import logic.waifu.PlayerLoader;
import logic.waifu.WaifuLoader;
import org.javacord.api.entity.channel.TextChannel;
import ui.messages.MessageSender;

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