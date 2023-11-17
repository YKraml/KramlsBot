package logic.timer;

import domain.GuessingGame;
import domain.exceptions.MyOwnException;
import logic.waifu.GuessingGameManager;
import logic.messages.MessageSender;
import org.javacord.api.entity.channel.TextChannel;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import ui.reaction.AnimeInfoReactionListenerBuilder;

public class RevealTimer {

    private final GuessingGameManager guessingGameManager;
    private final MessageSender messageSender;
    private final String serverId;
    private final String url;
    private final TextChannel channel;
    private final ScheduledExecutorService scheduledExecutorService;
    private final AnimeInfoReactionListenerBuilder animeInfoReactionListenerBuilder;

    public RevealTimer(GuessingGameManager guessingGameManager, MessageSender messageSender, String serverId, String url, TextChannel channel, ScheduledExecutorService scheduledExecutorService,
        AnimeInfoReactionListenerBuilder animeInfoReactionListenerBuilder) {
        this.guessingGameManager = guessingGameManager;
        this.messageSender = messageSender;
        this.serverId = serverId;
        this.url = url;
        this.channel = channel;
        this.scheduledExecutorService = scheduledExecutorService;
        this.animeInfoReactionListenerBuilder = animeInfoReactionListenerBuilder;
    }

    public void startTimer() {
        Runnable showMessageRunnable = () -> {
            try {
                showMessage();
            } catch (MyOwnException ignored) {
                //Ignore.
            }
        };
        scheduledExecutorService.schedule(showMessageRunnable, 1, TimeUnit.MINUTES);

    }

    private void showMessage() throws MyOwnException {
        GuessingGame game = guessingGameManager.getGuessingGameByServer(serverId);
        boolean sameGameIsStillRunning = game.getUrl().equals(url);
        if (sameGameIsStillRunning) {
            guessingGameManager.removeGuessGame(serverId);
            messageSender.sendTimeIsUpMessage(channel);
            messageSender.sendGuessGameEnd(channel, game, animeInfoReactionListenerBuilder);
        }
    }


}
