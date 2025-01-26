package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.waifu.Waifu;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.messages.MyMessageAbs;

public class WaifuToClaimWas extends MyMessageAbs {

  private final Waifu waifu;

  public WaifuToClaimWas(Waifu waifu) {
    this.waifu = waifu;
  }

  @Override
  public void startRoutine(Message message) throws MyOwnException {
    //Ignore.
  }

  @Override
  public EmbedBuilder getContent() throws MyOwnException {
    return this.convertStringToEmbed(
        "Die Waifu hiess \"" + waifu.getName() + "\" von \"" + waifu.getAnimeName() + "\"");
  }
}
