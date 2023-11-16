package logic.routines;

import domain.exceptions.MyOwnException;
import domain.exceptions.messages.NotEnoughResource;
import domain.waifu.Player;
import domain.waifu.Waifu;
import logic.waifu.JikanFetcher;
import logic.waifu.PlayerLoader;
import logic.waifu.WaifuBuilder;
import logic.waifu.WaifuLoader;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;
import ui.commands.Answer;
import ui.messages.MessageSender;
import ui.messages.messages.WaifuSpawned;
import ui.messages.messages.WaifuStats;

public class RoutineSpawnWaifuCommand extends Routine {

    private final TextChannel channel;
    private final User user;
    private final WaifuBuilder waifuBuilder;
    private final PlayerLoader playerLoader;
    private final WaifuLoader waifuLoader;
    private final JikanFetcher jikanFetcher;
    private final MessageSender messageSender;

    public RoutineSpawnWaifuCommand(TextChannel channel, User user, WaifuBuilder waifuBuilder,
                                    PlayerLoader playerLoader, WaifuLoader waifuLoader, JikanFetcher jikanFetcher,
                                    MessageSender messageSender) {
        this.channel = channel;
        this.user = user;
        this.waifuBuilder = waifuBuilder;
        this.playerLoader = playerLoader;
        this.waifuLoader = waifuLoader;
        this.jikanFetcher = jikanFetcher;
        this.messageSender = messageSender;
    }

    @Override
    Answer start(RoutineRunner routineRunner) throws MyOwnException {
        Player player = playerLoader.getPlayerByUser(user);
        if (player.getInventory().getMoney() < 1000) {
            throw new MyOwnException(
                    new NotEnoughResource(player.getInventory().getMoney(), 1000, "Geld"), null);
        }

        Waifu waifu = waifuBuilder.createRandomWaifu();
        player.addWaifu(waifu);

        player.getInventory().removeMoney(1000);

        messageSender.send(new WaifuSpawned(player), channel);
        messageSender.send(
                new WaifuStats(waifu, player, playerLoader, waifuLoader, jikanFetcher, messageSender),
                channel);
        playerLoader.savePlayer(player);

        return new Answer("Spawned a Waifu.");
    }
}
