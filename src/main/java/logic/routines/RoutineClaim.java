package logic.routines;

import domain.Answer;
import domain.exceptions.MyOwnException;
import domain.exceptions.messages.ReachedMaxWaifus;
import domain.waifu.Player;
import domain.waifu.Waifu;
import logic.messages.MessageSender;
import logic.waifu.JikanFetcher;
import domain.PlayerLoader;
import domain.WaifuLoader;
import logic.waifu.WaifuSpawnManager;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.util.Optional;

public class RoutineClaim extends Routine {

    private final Server server;
    private final TextChannel channel;
    private final User user;
    private final String guess;
    private final WaifuSpawnManager waifuSpawnManager;
    private final PlayerLoader playerLoader;
    private final WaifuLoader waifuLoader;
    private final JikanFetcher jikanFetcher;
    private final MessageSender messageSender;

    public RoutineClaim(Server server, TextChannel channel, User user, String guess, WaifuSpawnManager waifuSpawnManager, PlayerLoader playerLoader, WaifuLoader waifuLoader, JikanFetcher jikanFetcher, MessageSender messageSender) {
        this.server = server;
        this.channel = channel;
        this.user = user;
        this.guess = guess;
        this.waifuSpawnManager = waifuSpawnManager;
        this.playerLoader = playerLoader;
        this.waifuLoader = waifuLoader;
        this.jikanFetcher = jikanFetcher;
        this.messageSender = messageSender;
    }

    @Override
    Answer start(RoutineRunner routineRunner) throws MyOwnException {
        Player player = playerLoader.getPlayerByUser(user);

        if (player.getWaifuList().size() >= player.getMaxWaifus()) {
            throw new MyOwnException(new ReachedMaxWaifus(player), null);
        }

        Optional<Waifu> waifu = waifuSpawnManager.guessWaifu(server.getIdAsString(), guess);
        if (waifu.isPresent()) {
            player.addWaifu(waifu.get());
            messageSender.sendGuessedRight(channel, player);
            messageSender.sendWaifuStats(channel, waifu.get(), player, playerLoader, waifuLoader, jikanFetcher, messageSender);
            playerLoader.savePlayer(player);
        } else {
            messageSender.sendGuessedWrong(channel, player);
        }

        return new Answer("Someone tried to claim a Waifu");
    }

}
