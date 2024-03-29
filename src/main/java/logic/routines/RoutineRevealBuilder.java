package logic.routines;

import com.google.inject.Inject;
import logic.waifu.GuessingGameManager;
import logic.messages.MessageSender;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import logic.AnimeInfoReactionListenerBuilder;

public class RoutineRevealBuilder {


    private final GuessingGameManager guessingGameManager;
    private final MessageSender messageSender;
    private final AnimeInfoReactionListenerBuilder animeInfoReactionListenerBuilder;

    @Inject
    public RoutineRevealBuilder(GuessingGameManager guessingGameManager, MessageSender messageSender,
        AnimeInfoReactionListenerBuilder animeInfoReactionListenerBuilder) {
        this.guessingGameManager = guessingGameManager;
        this.messageSender = messageSender;
        this.animeInfoReactionListenerBuilder = animeInfoReactionListenerBuilder;
    }

    public RoutineReveal createRoutineReveal(Server server, TextChannel channel) {
        return new RoutineReveal(server, channel, guessingGameManager, messageSender,
            animeInfoReactionListenerBuilder);
    }
}