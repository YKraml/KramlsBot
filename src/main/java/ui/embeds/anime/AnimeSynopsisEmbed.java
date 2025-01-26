package ui.embeds.anime;

import model.jikan.anime.animeByIdFull.AnimeFullById;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import util.Colors;

public class AnimeSynopsisEmbed extends EmbedBuilder {

  public AnimeSynopsisEmbed(AnimeFullById anime) {
    this.setTitle("Synopsis");
    this.setColor(Colors.COLOR);
    this.setDescription(anime.getData().getSynopsis());
  }
}
