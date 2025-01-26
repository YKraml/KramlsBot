package logic.routines;

import com.google.inject.Inject;
import domain.PlayerLoader;
import logic.MusicPlayerManager;
import logic.discord.ChannelFinder;
import logic.messages.MessageSender;
import logic.youtube.YoutubeFetcher;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

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