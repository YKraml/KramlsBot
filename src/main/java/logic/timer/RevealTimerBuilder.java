package logic.timer;

import com.google.inject.Inject;
import logic.waifu.GuessingGameManager;
import logic.messages.MessageSender;
import org.javacord.api.entity.channel.TextChannel;

import java.util.concurrent.ScheduledExecutorService;

public class RevealTimerBuilder {

    private final GuessingGameManager guessingGameManager;
    private final MessageSender messageSender;
    private final ScheduledExecutorService scheduledExecutorService;

    @Inject
    public RevealTimerBuilder(GuessingGameManager guessingGameManager, MessageSender messageSender,
                              ScheduledExecutorService scheduledExecutorService) {
        this.guessingGameManager = guessingGameManager;
        this.messageSender = messageSender;
        this.scheduledExecutorService = scheduledExecutorService;
    }

    public RevealTimer createRevealTimer(TextChannel channel, String serverId, String url) {
        return new RevealTimer(guessingGameManager, messageSender, serverId, url,
                channel, scheduledExecutorService);
    }
}