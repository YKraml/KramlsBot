package logic.timer;

import com.google.inject.Inject;
import logic.MessageSender;
import logic.music.guess.GuessingGameManager;
import org.javacord.api.entity.channel.TextChannel;
import ui.messages.messages.GuessGameEndBuilder;

import java.util.concurrent.ScheduledExecutorService;

public class RevealTimerBuilder {

    private final GuessingGameManager guessingGameManager;
    private final MessageSender messageSender;
    private final GuessGameEndBuilder guessGameEndBuilder;
    private final ScheduledExecutorService scheduledExecutorService;

    @Inject
    public RevealTimerBuilder(GuessingGameManager guessingGameManager, MessageSender messageSender,
                              GuessGameEndBuilder guessGameEndBuilder, ScheduledExecutorService scheduledExecutorService) {
        this.guessingGameManager = guessingGameManager;
        this.messageSender = messageSender;
        this.guessGameEndBuilder = guessGameEndBuilder;
        this.scheduledExecutorService = scheduledExecutorService;
    }

    public RevealTimer createRevealTimer(TextChannel channel, String serverId, String url) {
        return new RevealTimer(guessingGameManager, messageSender, guessGameEndBuilder, serverId, url,
                channel, scheduledExecutorService);
    }
}