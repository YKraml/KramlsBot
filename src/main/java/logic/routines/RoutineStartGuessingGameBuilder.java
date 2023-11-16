package logic.routines;

import com.google.inject.Inject;
import logic.music.audio.MusicPlayerManager;
import logic.music.guess.GuessingGameManager;
import logic.waifu.JikanFetcher;
import logic.youtube.YoutubeFetcher;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import logic.MessageSender;
import ui.timer.RevealTimerBuilder;
import util.ChannelFinder;

public class RoutineStartGuessingGameBuilder {

    private final GuessingGameManager guessingGameManager;
    private final JikanFetcher jikanFetcher;
    private final YoutubeFetcher youtubeFetcher;
    private final ChannelFinder channelFinder;
    private final MusicPlayerManager musicPlayerManager;
    private final MessageSender messageSender;
    private final RoutineRevealBuilder routineRevealBuilder;

    @Inject
    public RoutineStartGuessingGameBuilder(GuessingGameManager guessingGameManager,
                                           JikanFetcher jikanFetcher, YoutubeFetcher youtubeFetcher, ChannelFinder channelFinder,
                                           MusicPlayerManager musicPlayerManager, MessageSender messageSender,
                                           RoutineRevealBuilder routineRevealBuilder) {
        this.guessingGameManager = guessingGameManager;
        this.jikanFetcher = jikanFetcher;
        this.youtubeFetcher = youtubeFetcher;
        this.channelFinder = channelFinder;
        this.musicPlayerManager = musicPlayerManager;
        this.messageSender = messageSender;
        this.routineRevealBuilder = routineRevealBuilder;
    }


    public RoutineStartGuessingGame createRoutineStartGuessingGame(Server server, TextChannel channel,
                                                                   User user, int difficulty, RevealTimerBuilder revealTimerTask) {
        return new RoutineStartGuessingGame(server, channel, user, difficulty, guessingGameManager,
                jikanFetcher, youtubeFetcher, channelFinder, musicPlayerManager, messageSender,
                routineRevealBuilder, revealTimerTask);
    }
}