package messages.messages;

import embeds.anime.CharacterEmbed;
import exceptions.MyOwnException;
import messages.MyMessage;
import model.jikan.anime.animeByIdFull.AnimeFullById;
import model.jikan.anime.animeCharacters.Datum;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class CharacterOverview extends MyMessage {

  private final AnimeFullById anime;
  private final Datum characterStaffEntry;

  public CharacterOverview(AnimeFullById anime, Datum characterStaffEntry) {
    super();
    this.anime = anime;
    this.characterStaffEntry = characterStaffEntry;
  }

  @Override
  protected void startRoutine(Message message) throws MyOwnException {
    //Ignore.
  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
    return new CharacterEmbed(anime, characterStaffEntry);
  }
}
