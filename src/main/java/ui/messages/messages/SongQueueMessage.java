package ui.messages.messages;

import domain.Emojis;
import domain.PlayerLoader;
import domain.exceptions.MyOwnException;
import domain.queue.Queue;
import logic.MusicPlayerManager;
import logic.messages.Observer;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.embeds.music.QueueEmbed;
import ui.messages.MyMessageAbs;
import ui.reaction.QueueListener;

public class SongQueueMessage extends MyMessageAbs {

  private final Queue queue;
  private final MusicPlayerManager musicPlayerManager;
  private final PlayerLoader playerLoader;

  public SongQueueMessage(Queue queueByServer, MusicPlayerManager musicPlayerManager,
      PlayerLoader playerLoader) {
    this.queue = queueByServer;
    this.musicPlayerManager = musicPlayerManager;
    this.playerLoader = playerLoader;
  }

  @Override
  public void startRoutine(Message message) throws MyOwnException {
    message.addReaction(Emojis.REWIND.getEmoji());
    message.addReaction(Emojis.ARROWS_COUNTERCLOCKWISE.getEmoji());
    message.addReaction(Emojis.FAST_FORWARD.getEmoji());
    message.addReaction(Emojis.STAR.getEmoji());
    message.addReactionAddListener(new QueueListener(queue, playerLoader, musicPlayerManager));

    Observer observer = () -> {
      message.edit(new QueueEmbed(queue));
    };

    musicPlayerManager.addQueueMessage(message, observer);
  }

  @Override
  public EmbedBuilder getContent() throws MyOwnException {
    return new QueueEmbed(queue);
  }


}
