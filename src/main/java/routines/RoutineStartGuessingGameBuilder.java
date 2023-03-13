package routines;

import actions.timer.RevealTimerBuilder;
import com.google.inject.Inject;
import discord.ChannelFinder;
import messages.MessageSender;
import music.guess.GuessingGameManager;
import music.audio.MusicPlayerManager;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import waifu.JikanFetcher;
import waifu.loader.PlayerLoader;
import youtube.YoutubeFetcher;

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