package logic.routines;

import domain.Answer;
import domain.GuessingGame;
import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import logic.waifu.GuessingGameManager;
import logic.messages.MessageSender;
import domain.PlayerLoader;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import logic.AnimeInfoReactionListenerBuilder;

public class RoutineGuess extends Routine {

    private final String guess;
    private final Server server;
    private final User user;
    private final TextChannel channel;
    private final GuessingGameManager guessingGameManager;
    private final PlayerLoader playerLoader;
    private final MessageSender messageSender;
    private final AnimeInfoReactionListenerBuilder animeInfoReactionListenerBuilder;

    public RoutineGuess(Server server, TextChannel channel, User user, String guess, GuessingGameManager guessingGameManager, PlayerLoader playerLoader, MessageSender messageSender,
        AnimeInfoReactionListenerBuilder animeInfoReactionListenerBuilder) {
        this.server = server;
        this.channel = channel;
        this.user = user;
        this.guess = guess;
        this.guessingGameManager = guessingGameManager;
        this.playerLoader = playerLoader;
        this.messageSender = messageSender;
        this.animeInfoReactionListenerBuilder = animeInfoReactionListenerBuilder;
    }

    @Override
    Answer start(RoutineRunner routineRunner) throws MyOwnException {
        Player player = playerLoader.getPlayerByUser(user);
        boolean guessedRight = guessingGameManager.makeGuess(guess, server.getIdAsString());
        if (guessedRight) {

            GuessingGame guessingGame = guessingGameManager.getGuessingGameByServer(server.getIdAsString());
            guessingGameManager.removeGuessGame(server.getIdAsString());

            int wonMoney = 100 * guessingGame.getDifficulty();
            player.addToGuessesRight(1);
            player.getInventory().addMoney(wonMoney);
            playerLoader.savePlayer(player);

            messageSender.sendGuessedRight(channel, player);
            messageSender.sendWonMoney(channel, player, wonMoney);
            messageSender.sendGuessGameEnd(channel, guessingGame, animeInfoReactionListenerBuilder);

        } else {
            channel.sendMessage(player.getNameTag() + ", du lagst falsch!");
        }

        return new Answer("Someone tried to guess the Song.");
    }
}
