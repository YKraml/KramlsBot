package ui.embeds.music;

import ui.embeds.MyListEmbed;
import java.nio.charset.StandardCharsets;
import domain.queue.Queue;
import domain.queue.QueueElement;

public class QueueEmbed extends MyListEmbed<QueueElement> {

  public QueueEmbed(Queue queue) {
    super("Musik Queue", queue.getNextElements(), 0, false);

    queue.getCurrentElement().ifPresent(queueElement -> {
      if (!queueElement.isSecret()) {
        setThumbnail(queueElement.getImageUrl());
      }
      setDescription("Im Moment: **%s** | %s".formatted(new String(
              queueElement.getDisplayTitle().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8),
          queueElement.getDisplayBody()));

    });

  }
}
