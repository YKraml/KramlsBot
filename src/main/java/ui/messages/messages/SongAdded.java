package ui.messages.messages;

import ui.messages.MyMessage;
import ui.reaction.SongAddedEmbedListener;
import util.Emojis;
import ui.embeds.music.SongAddedEmbed;
import domain.exceptions.MyOwnException;
import logic.music.audio.MusicPlayerManager;
import domain.queue.QueueElement;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import logic.waifu.PlayerLoader;

public class SongAdded extends MyMessage {

  private final QueueElement queueElement;
  private final MusicPlayerManager musicPlayerManager;
  private final PlayerLoader playerLoader;

  public SongAdded(QueueElement queueElement, MusicPlayerManager musicPlayerManager,
      PlayerLoader playerLoader) {
    this.queueElement = queueElement;
    this.musicPlayerManager = musicPlayerManager;
    this.playerLoader = playerLoader;
  }

  @Override
  protected void startRoutine(Message message) throws MyOwnException {
    message.addReaction(Emojis.INFORMATION_SOURCE.getEmoji());
    message.addReactionAddListener(new SongAddedEmbedListener(musicPlayerManager, playerLoader));
  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
    return new SongAddedEmbed(queueElement);
  }

}
