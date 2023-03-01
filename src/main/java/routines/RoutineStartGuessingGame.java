package routines;

import actions.commands.Answer;
import actions.timer.RevealTimerBuilder;
import discord.ChannelFinder;
import exceptions.MyOwnException;
import exceptions.messages.GuessingGameAlreadyExists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import messages.MessageSender;
import messages.messages.GuessGameStarted;
import model.jikan.anime.animeByIdFull.AnimeFullById;
import music.GuessingGameManager;
import music.audio.MusicPlayerManager;
import music.audio.QueueElement;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import waifu.JikanFetcher;
import youtube.YoutubeFetcher;

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

  public RoutineStartGuessingGame(Server server, TextChannel textChannel, User user, int difficulty,
      GuessingGameManager guessingGameManager, JikanFetcher jikanFetcher,
      YoutubeFetcher youtubeFetcher, ChannelFinder channelFinder,
      MusicPlayerManager musicPlayerManager, MessageSender messageSender,
      RoutineRevealBuilder routineRevealBuilder, RevealTimerBuilder revealTimerBuilder) {
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


  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {
    String serverId = server.getIdAsString();
    if (guessingGameManager.gameExists(serverId)) {
      throw new MyOwnException(new GuessingGameAlreadyExists(), null);
    }

    AnimeFullById anime = jikanFetcher.getRandomPopularAnimeFromPage(difficulty);
    String song = getRandomSong(anime);
    String animeTitle = anime.getData().getTitle();

    String url = "https://www.youtube.com/watch?v=%s".formatted(
        youtubeFetcher.getIdByVideoName("Anime %s Song %s".formatted(animeTitle, song)));

    musicPlayerManager.playThisSongNext(channelFinder.getServerVoiceChannelByMember(server, user),
        textChannel, new QueueElement(animeTitle, url, user.getName(), true));

    revealTimerBuilder.createRevealTimer(textChannel, serverId, url).startTimer();

    guessingGameManager.startGuessingGame(anime, url, song, serverId, difficulty);
    messageSender.send(new GuessGameStarted(routineRunner, routineRevealBuilder,
        guessingGameManager.getGuessingGameByServer(serverId)), textChannel);

    return new Answer(
        "Someone started guessing game. Anime = '%s', Song = '%s', URL = '%s'".formatted(animeTitle,
            song, url));

  }

  private static String getRandomSong(AnimeFullById anime) {
    List<String> songNames = new ArrayList<>();
    songNames.addAll(anime.getData().getTheme().getOpenings());
    songNames.addAll(anime.getData().getTheme().getEndings());
    Collections.shuffle(songNames);
    return songNames.iterator().next();
  }

}
