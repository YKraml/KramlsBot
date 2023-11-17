package ui.embeds.anime;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import domain.Emojis;
import util.Main;

import java.util.List;

public class AnimeSongEmbed extends EmbedBuilder {

    public AnimeSongEmbed(String type, List<String> songs, int page) {

        this.setTitle(type);
        this.setColor(Main.COLOR);
        this.setDescription("Page: " + (page + 1));

        for (int i = page * 10; i < (page + 1) * 10; i++) {
            if (songs.size() <= i) {
                break;
            }
            this.addField(Emojis.getCountEmojis()[i % 10], songs.get(i));
        }

    }


}
