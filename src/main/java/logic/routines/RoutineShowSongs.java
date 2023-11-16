package logic.routines;

import domain.Answer;
import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import logic.MessageSender;
import logic.music.audio.MusicPlayerManager;
import logic.waifu.PlayerLoader;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import ui.messages.messages.LikedSongs;

public class RoutineShowSongs extends Routine {

    private final MessageSender messageSender;
    private final Server server;
    private final User user;
    private final PlayerLoader playerLoader;
    private final MusicPlayerManager musicPlayerManager;
    private final TextChannel channel;

    public RoutineShowSongs(MessageSender messageSender, Server server, User user,
                            PlayerLoader playerLoader, MusicPlayerManager musicPlayerManager, TextChannel channel) {
        this.messageSender = messageSender;
        this.server = server;
        this.user = user;
        this.playerLoader = playerLoader;
        this.musicPlayerManager = musicPlayerManager;
        this.channel = channel;
    }

    @Override
    Answer start(RoutineRunner routineRunner) throws MyOwnException {
        Player player = playerLoader.getPlayerByUser(user);
        messageSender.send(new LikedSongs(player, server, user, playerLoader,
                messageSender, musicPlayerManager), channel);

        return new Answer("Someone showed his liked songs");
    }
}
