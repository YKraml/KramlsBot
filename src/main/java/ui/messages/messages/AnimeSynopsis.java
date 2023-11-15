package ui.messages.messages;

import ui.embeds.anime.AnimeSynopsisEmbed;
import domain.exceptions.MyOwnException;
import ui.messages.MyMessage;
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
