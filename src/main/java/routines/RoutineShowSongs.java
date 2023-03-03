package routines;

import actions.listeners.commands.Answer;
import exceptions.MyOwnException;
import messages.MessageSender;
import messages.messages.LikedSongs;
import music.audio.MusicPlayerManager;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import waifu.loader.PlayerLoader;
import waifu.model.Player;

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
