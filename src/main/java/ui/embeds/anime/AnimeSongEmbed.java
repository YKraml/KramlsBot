package ui.embeds.anime;

import domain.Emojis;
import java.util.List;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import util.Colors;

public class AnimeSongEmbed extends EmbedBuilder {

  public AnimeSongEmbed(String type, List<String> songs, int page) {

    this.setTitle(type);
    this.setColor(Colors.COLOR);
    this.setDescription("Page: " + (page + 1));

    for (int i = page * 10; i < (page + 1) * 10; i++) {
      if (songs.size() <= i) {
        break;
      }
      this.addField(Emojis.getCountEmojis()[i % 10], songs.get(i));
    }

  }


}
