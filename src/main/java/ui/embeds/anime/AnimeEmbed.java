package ui.embeds.anime;

import model.jikan.anime.animeByIdFull.AnimeFullById;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import util.Colors;

public class AnimeEmbed extends EmbedBuilder {

  private static final String DELIMITER = " | ";

  public AnimeEmbed(AnimeFullById anime) {

    String episodes;
    if (anime.getData().getEpisodes() == null) {
      episodes = anime.getData().getStatus();
    } else {
      episodes = "Episodes: " + String.format("%d", anime.getData().getEpisodes());
    }

    this.setTitle(anime.getData().getTitle());
    this.setDescription("Score: " + anime.getData().getScore()
        + DELIMITER + episodes
        + DELIMITER + anime.getData().getType()
        + DELIMITER + "Popularity: " + anime.getData().getPopularity()
        + DELIMITER + "Ranked: " + anime.getData().getRank()
    );
    this.setColor(Colors.COLOR);
    this.setUrl(anime.getData().getUrl());

    if (anime.getData().getSynopsis().length() > 200) {
      this.addField("Synopsis", anime.getData().getSynopsis().substring(0, 197) + "...");
    } else {
      this.addField("Synopsis", anime.getData().getSynopsis());
    }
    if (anime.getData().getTrailer().getUrl() != null) {
      this.addField("Trailer", anime.getData().getTrailer().getUrl());
    }

    this.setImage(anime.getData().getImages().getJpg().getImageUrl());
  }

}
