package messages.messages;

import exceptions.MyOwnException;
import messages.MyMessage;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class WaifuNotFound extends MyMessage {

  private final int waifuNumber;

  public WaifuNotFound(int waifuNumber) {
    this.waifuNumber = waifuNumber;
  }

  @Override
  protected void startRoutine(Message message) throws MyOwnException {
    //Ignore.
  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
    return this.convertStringToEmbed("Konnte Waifu mit der Nr. " + waifuNumber + " nicht finden.");
  }
}
