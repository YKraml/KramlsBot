package logic.timer;

import com.google.inject.Inject;
import logic.waifu.GuessingGameManager;
import logic.messages.MessageSender;
import org.javacord.api.entity.channel.TextChannel;

import java.util.concurrent.ScheduledExecutorService;
import ui.reaction.AnimeInfoReactionListenerBuilder;

public class RevealTimerBuilder {

    private final GuessingGameManager guessingGameManager;
    private final MessageSender messageSender;
    private final ScheduledExecutorService scheduledExecutorService;
    private final AnimeInfoReactionListenerBuilder animeInfoReactionListenerBuilder;

    @Inject
    public RevealTimerBuilder(GuessingGameManager guessingGameManager, MessageSender messageSender,
                              ScheduledExecutorService scheduledExecutorService,
        AnimeInfoReactionListenerBuilder animeInfoReactionListenerBuilder) {
        this.guessingGameManager = guessingGameManager;
        this.messageSender = messageSender;
        this.scheduledExecutorService = scheduledExecutorService;
        this.animeInfoReactionListenerBuilder = animeInfoReactionListenerBuilder;
    }

    public RevealTimer createRevealTimer(TextChannel channel, String serverId, String url) {
        return new RevealTimer(guessingGameManager, messageSender, serverId, url,
                channel, scheduledExecutorService, animeInfoReactionListenerBuilder);
    }
}