package messages.messages;

import embeds.anime.AnimeSynopsisEmbed;
import exceptions.MyOwnException;
import messages.MyMessage;
import model.jikan.anime.animeByIdFull.AnimeFullById;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class AnimeSynopsis extends MyMessage {

  private final AnimeFullById anime;

  public AnimeSynopsis(AnimeFullById anime) {
    this.anime = anime;
  }

  @Override
  protected void startRoutine(Message message) throws MyOwnException {
    //Just ignore.
  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
    return new AnimeSynopsisEmbed(anime);
  }
}
