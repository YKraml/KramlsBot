package logic.routines;

import com.google.inject.Inject;
import logic.MessageSender;
import logic.music.guess.GuessingGameManager;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;

public class RoutineRevealBuilder {


    private final GuessingGameManager guessingGameManager;
    private final MessageSender messageSender;

    @Inject
    public RoutineRevealBuilder(GuessingGameManager guessingGameManager, MessageSender messageSender) {
        this.guessingGameManager = guessingGameManager;
        this.messageSender = messageSender;
    }

    public RoutineReveal createRoutineReveal(Server server, TextChannel channel) {
        return new RoutineReveal(server, channel, guessingGameManager, messageSender
        );
    }
}