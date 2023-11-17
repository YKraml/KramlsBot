package logic.routines;

import domain.Answer;
import domain.exceptions.MyOwnException;
import domain.exceptions.messages.GuessingGameAlreadyExists;
import domain.queue.QueueElement;
import logic.waifu.GuessingGameManager;
import logic.MusicPlayerManager;
import logic.discord.ChannelFinder;
import logic.messages.MessageSender;
import logic.timer.RevealTimerBuilder;
import logic.waifu.JikanFetcher;
import logic.youtube.YoutubeFetcher;
import model.jikan.anime.animeByIdFull.AnimeFullById;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoutineStartGuessingGame extends Routine {

    private final int difficulty;
    private final Server server;
    private final User user;
    private final TextChannel textChannel;
    private final GuessingGameManager guessingGameManager;
    private final JikanFetcher jikanFetcher;
    private final YoutubeFetcher youtubeFetcher;
    private final ChannelFinder channelFinder;
    private final MusicPlayerManager musicPlayerManager;
    private final MessageSender messageSender;
    private final RoutineRevealBuilder routineRevealBuilder;
    private final RevealTimerBuilder revealTimerBuilder;

    public RoutineStartGuessingGame(Server server, TextChannel textChannel, User user, int difficulty, GuessingGameManager guessingGameManager, JikanFetcher jikanFetcher, YoutubeFetcher youtubeFetcher, ChannelFinder channelFinder, MusicPlayerManager musicPlayerManager, MessageSender messageSender, RoutineRevealBuilder routineRevealBuilder, RevealTimerBuilder revealTimerBuilder) {
        this.difficulty = difficulty;
        this.server = server;
        this.user = user;
        this.textChannel = textChannel;
        this.guessingGameManager = guessingGameManager;
        this.jikanFetcher = jikanFetcher;
        this.youtubeFetcher = youtubeFetcher;
        this.channelFinder = channelFinder;
        this.musicPlayerManager = musicPlayerManager;
        this.messageSender = messageSender;
        this.routineRevealBuilder = routineRevealBuilder;
        this.revealTimerBuilder = revealTimerBuilder;
    }

    private static String getRandomSong(AnimeFullById anime) {
        List<String> songNames = new ArrayList<>();
        songNames.addAll(anime.getData().getTheme().getOpenings());
        songNames.addAll(anime.getData().getTheme().getEndings());
        Collections.shuffle(songNames);
        return songNames.iterator().next();
    }

    @Override
    Answer start(RoutineRunner routineRunner) throws MyOwnException {
        String serverId = server.getIdAsString();
        if (guessingGameManager.gameExists(serverId)) {
            throw new MyOwnException(new GuessingGameAlreadyExists(), null);
        }

        try {
            AnimeFullById anime = jikanFetcher.getRandomPopularAnimeFromPage(difficulty);
            String song = getRandomSong(anime);
            String animeTitle = anime.getData().getTitle();

            String url = "https://www.youtube.com/watch?v=%s".formatted(youtubeFetcher.getIdByVideoName("Anime %s Song %s".formatted(animeTitle, song)));

            ServerVoiceChannel voiceChannel = channelFinder.getServerVoiceChannelByMember(server, user);
            QueueElement queueElement = new QueueElement(animeTitle, url, user.getName(), true);
            musicPlayerManager.playThisSongNext(voiceChannel, textChannel, queueElement);

            guessingGameManager.startGuessingGame(anime, url, song, serverId, difficulty);
            revealTimerBuilder.createRevealTimer(textChannel, serverId, url).startTimer();
            messageSender.sendGuessGameStarted(textChannel, routineRunner, routineRevealBuilder);

            return new Answer("Someone started guessing game. Anime = '%s', Song = '%s', URL = '%s'".formatted(animeTitle, song, url));

        } catch (Exception e) {
            guessingGameManager.removeGuessGame(serverId);
            throw e;
        }


    }

}
