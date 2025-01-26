package ui.embeds.music;

import domain.queue.QueueElement;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import util.Colors;

public class SongAddedEmbed extends EmbedBuilder {

  public SongAddedEmbed(QueueElement queueElement) {

    this.setColor(Colors.COLOR);
    this.setThumbnail(queueElement.getImageUrl());
    this.setTitle("Song wurde zur Queue hinzugefuegt");
    this.addField(queueElement.getDisplayTitle(), queueElement.getDisplayBody());

  }
}
