package logic.routines;

import com.google.inject.Inject;
import domain.PlayerLoader;
import logic.MusicPlayerManager;
import logic.messages.MessageSender;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class RoutineShowSongsBuilder {

  private final MessageSender messageSender;
  private final PlayerLoader playerLoader;
  private final MusicPlayerManager musicPlayerManager;

  @Inject
  public RoutineShowSongsBuilder(MessageSender messageSender, PlayerLoader playerLoader,
      MusicPlayerManager musicPlayerManager) {
    this.messageSender = messageSender;
    this.playerLoader = playerLoader;
    this.musicPlayerManager = musicPlayerManager;
  }

  public RoutineShowSongs createRoutineShowSongs(Server server, TextChannel channel, User user) {
    return new RoutineShowSongs(messageSender, server, user, playerLoader,
        musicPlayerManager, channel);
  }
}