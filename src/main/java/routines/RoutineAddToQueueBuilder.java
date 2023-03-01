package routines;

import com.google.inject.Inject;
import discord.ChannelFinder;
import messages.MessageSender;
import music.audio.MusicPlayerManager;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import waifu.loader.PlayerLoader;
import youtube.YoutubeFetcher;

public class RoutineAddToQueueBuilder {

  private final MusicPlayerManager musicPlayerManager;
  private final YoutubeFetcher youtubeFetcher;
  private final MessageSender messageSender;
  private final ChannelFinder channelFinder;
  private final PlayerLoader playerLoader;

  @Inject
  public RoutineAddToQueueBuilder(MusicPlayerManager musicPlayerManager,
      YoutubeFetcher youtubeFetcher, MessageSender messageSender, ChannelFinder channelFinder,
      PlayerLoader playerLoader) {
    this.musicPlayerManager = musicPlayerManager;
    this.youtubeFetcher = youtubeFetcher;
    this.messageSender = messageSender;
    this.channelFinder = channelFinder;
    this.playerLoader = playerLoader;
  }


  public RoutineAddToQueue createRoutineAddToQueue(Server server, TextChannel channel, User user,
      String input) {
    return new RoutineAddToQueue(server, channel, user, input, musicPlayerManager,
        youtubeFetcher, messageSender, channelFinder, playerLoader);
  }
}