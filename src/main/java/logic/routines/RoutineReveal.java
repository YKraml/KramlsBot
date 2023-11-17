package logic.routines;

import domain.Answer;
import domain.GuessingGame;
import domain.exceptions.MyOwnException;
import logic.MessageSender;
import logic.music.guess.GuessingGameManager;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;

public class RoutineReveal extends Routine {

    private final TextChannel channel;
    private final Server server;
    private final GuessingGameManager guessingGameManager;
    private final MessageSender messageSender;

    public RoutineReveal(Server server, TextChannel channel, GuessingGameManager guessingGameManager, MessageSender messageSender) {
        this.server = server;
        this.channel = channel;
        this.guessingGameManager = guessingGameManager;
        this.messageSender = messageSender;
    }

    @Override
    Answer start(RoutineRunner routineRunner) throws MyOwnException {
        GuessingGame game = guessingGameManager.getGuessingGameByServer(server.getIdAsString());
        messageSender.sendGuessGameEnd(channel, game);
        guessingGameManager.removeGuessGame(server.getIdAsString());
        return new Answer("Guessing game was terminated");
    }
}
