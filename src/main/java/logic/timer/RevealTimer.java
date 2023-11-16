package logic.timer;

import domain.GuessingGame;
import domain.exceptions.MyOwnException;
import logic.MessageSender;
import logic.music.guess.GuessingGameManager;
import org.javacord.api.entity.channel.TextChannel;
import ui.messages.messages.GuessGameEndBuilder;
import ui.messages.messages.TimeIsUpMessage;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RevealTimer {

    private final GuessingGameManager guessingGameManager;
    private final MessageSender messageSender;
    private final GuessGameEndBuilder guessGameEndBuilder;
    private final String serverId;
    private final String url;
    private final TextChannel channel;
    private final ScheduledExecutorService scheduledExecutorService;

    public RevealTimer(GuessingGameManager guessingGameManager, MessageSender messageSender,
                       GuessGameEndBuilder guessGameEndBuilder, String serverId, String url, TextChannel channel,
                       ScheduledExecutorService scheduledExecutorService) {
        this.guessingGameManager = guessingGameManager;
        this.messageSender = messageSender;
        this.guessGameEndBuilder = guessGameEndBuilder;
        this.serverId = serverId;
        this.url = url;
        this.channel = channel;
        this.scheduledExecutorService = scheduledExecutorService;
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
            messageSender.send(new TimeIsUpMessage(), channel);
            messageSender.send(guessGameEndBuilder.createGuessGameEnd(game), channel);
        }
    }


}
