package ui.embeds.anime;

import model.jikan.anime.animeByIdFull.AnimeFullById;
import model.jikan.anime.animeCharacters.Datum;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import util.Colors;

public class CharacterEmbed extends EmbedBuilder {

  public CharacterEmbed(AnimeFullById anime, Datum characterStaffEntry) {

    this.setTitle(characterStaffEntry.getCharacter().getName());
    this.setDescription(characterStaffEntry.getRole() + " | " + anime.getData().getTitle());
    this.setColor(Colors.COLOR);
    this.setImage(characterStaffEntry.getCharacter().getImages().getJpg().getImageUrl());

  }
}
