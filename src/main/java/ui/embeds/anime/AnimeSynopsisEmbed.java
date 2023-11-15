package ui.embeds.anime;

import util.Main;
import model.jikan.anime.animeByIdFull.AnimeFullById;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class AnimeSynopsisEmbed extends EmbedBuilder {

    public AnimeSynopsisEmbed(AnimeFullById anime) {
        this.setTitle("Synopsis");
      this.setColor(Main.COLOR);
        this.setDescription(anime.getData().getSynopsis());
    }
}
