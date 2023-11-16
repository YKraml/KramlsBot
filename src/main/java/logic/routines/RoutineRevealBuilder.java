package logic.routines;

import com.google.inject.Inject;
import logic.music.guess.GuessingGameManager;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import ui.messages.MessageSender;
import ui.messages.messages.GuessGameEndBuilder;

public class RoutineRevealBuilder {


    private final GuessingGameManager guessingGameManager;
    private final MessageSender messageSender;
    private final GuessGameEndBuilder guessGameEndBuilder;

    @Inject
    public RoutineRevealBuilder(GuessingGameManager guessingGameManager, MessageSender messageSender,
                                GuessGameEndBuilder guessGameEndBuilder) {
        this.guessingGameManager = guessingGameManager;
        this.messageSender = messageSender;
        this.guessGameEndBuilder = guessGameEndBuilder;
    }

    public RoutineReveal createRoutineReveal(Server server, TextChannel channel) {
        return new RoutineReveal(server, channel, guessingGameManager, messageSender,
                guessGameEndBuilder);
    }
}