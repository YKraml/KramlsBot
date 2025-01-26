package ui.reaction;

import domain.PlayerLoader;
import domain.exceptions.MyOwnException;
import logic.MusicPlayerManager;
import logic.messages.MessageSender;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import ui.messages.messages.SongQueueMessage;

public class SongAddedEmbedListener extends MyAbstractReactionListener {

  private final MusicPlayerManager musicPlayerManager;
  private final PlayerLoader playerLoader;
  private final MessageSender messageSender;

  public SongAddedEmbedListener(MusicPlayerManager musicPlayerManager, PlayerLoader playerLoader,
      MessageSender messageSender) {
    this.musicPlayerManager = musicPlayerManager;
    this.playerLoader = playerLoader;
    this.messageSender = messageSender;
  }

  @Override
  protected void startRoutine(DiscordApi discordApi, Server server, TextChannel textChannel,
      Message message, User user, Emoji emoji) throws MyOwnException {
    messageSender.send(new SongQueueMessage(musicPlayerManager.getQueueByServer(server),
        musicPlayerManager, playerLoader), textChannel);
  }
}
