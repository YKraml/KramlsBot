package embeds.music;

import embeds.MyListEmbed;
import music.audio.QueueElement;
import music.audio.Queue;

import java.nio.charset.StandardCharsets;

public class QueueEmbed extends MyListEmbed<QueueElement> {

    public QueueEmbed(Queue queue) {
        super("Musik Queue", queue.getNextElements(), 0, false);


        QueueElement e = queue.getCurrentElement();
        if (!e.isSecret()) {
            this.setThumbnail(e.getImageUrl());
        }
        this.setDescription("Im Moment: **" + new String(e.getDisplayTitle().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8) + "** | " + e.getDisplayBody());
    }
}
