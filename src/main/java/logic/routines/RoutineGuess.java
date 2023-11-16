package logic.routines;

import domain.GuessingGame;
import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import logic.music.guess.GuessingGameManager;
import logic.waifu.PlayerLoader;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import domain.Answer;
import ui.messages.MessageSender;
import ui.messages.messages.GuessGameEndBuilder;
import ui.messages.messages.GuessedRight;
import ui.messages.messages.WonMoney;

public class RoutineGuess extends Routine {

    private final String guess;
    private final Server server;
    private final User user;
    private final TextChannel channel;
    private final GuessingGameManager guessingGameManager;
    private final PlayerLoader playerLoader;
    private final MessageSender messageSender;
    private final GuessGameEndBuilder guessGameEndBuilder;

    public RoutineGuess(Server server, TextChannel channel, User user, String guess,
                        GuessingGameManager guessingGameManager, PlayerLoader playerLoader,
                        MessageSender messageSender, GuessGameEndBuilder guessGameEndBuilder) {
        this.server = server;
        this.channel = channel;
        this.user = user;
        this.guess = guess;
        this.guessingGameManager = guessingGameManager;
        this.playerLoader = playerLoader;
        this.messageSender = messageSender;
        this.guessGameEndBuilder = guessGameEndBuilder;
    }

    @Override
    Answer start(RoutineRunner routineRunner) throws MyOwnException {
        Player player = playerLoader.getPlayerByUser(user);
        boolean guessedRight = guessingGameManager.makeGuess(guess, server.getIdAsString());
        if (guessedRight) {

            GuessingGame guessingGame = guessingGameManager.getGuessingGameByServer(
                    server.getIdAsString());
            guessingGameManager.removeGuessGame(server.getIdAsString());

            int wonMoney = 100 * guessingGame.getDifficulty();
            player.addToGuessesRight(1);
            player.getInventory().addMoney(wonMoney);
            playerLoader.savePlayer(player);

            messageSender.send(new GuessedRight(player), channel);
            messageSender.send(new WonMoney(player, wonMoney), channel);
            messageSender.send(guessGameEndBuilder.createGuessGameEnd(guessingGame), channel);

        } else {
            channel.sendMessage(player.getNameTag() + ", du lagst falsch!");
        }

        return new Answer("Someone tried to guess the Song.");
    }
}
