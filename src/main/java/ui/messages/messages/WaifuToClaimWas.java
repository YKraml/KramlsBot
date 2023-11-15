package ui.messages.messages;

import domain.exceptions.MyOwnException;
import ui.messages.MyMessage;
import domain.waifu.Waifu;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class WaifuToClaimWas extends MyMessage {

  private final Waifu waifu;

  public WaifuToClaimWas(Waifu waifu) {
    this.waifu = waifu;
  }

  @Override
  protected void startRoutine(Message message) throws MyOwnException {
    //Ignore.
  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
    return this.convertStringToEmbed(
        "Die Waifu hiess \"" + waifu.getName() + "\" von \"" + waifu.getAnimeName() + "\"");
  }
}