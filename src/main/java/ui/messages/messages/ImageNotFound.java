package ui.messages.messages;

import domain.exceptions.MyOwnException;
import ui.messages.MyMessage;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import domain.waifu.Waifu;

public class ImageNotFound extends MyMessage {

  private final Waifu waifu;

  public ImageNotFound(Waifu waifu) {
    this.waifu = waifu;
  }

  @Override
  protected void startRoutine(Message message) throws MyOwnException {
    //Ignore.
  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
    return convertStringToEmbed("Konnte kein Bild zu '%s' finden.".formatted(waifu.getName()));
  }
}