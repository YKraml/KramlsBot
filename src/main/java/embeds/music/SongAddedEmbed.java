package embeds.music;

import de.kraml.Main;
import music.queue.QueueElement;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class SongAddedEmbed extends EmbedBuilder {

    public SongAddedEmbed(QueueElement queueElement) {

      this.setColor(Main.COLOR);
        this.setThumbnail(queueElement.getImageUrl());
        this.setTitle("Song wurde zur Queue hinzugefuegt");
        this.addField(queueElement.getDisplayTitle(), queueElement.getDisplayBody());

    }
}
