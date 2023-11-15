package ui.messages.messages;

import ui.embeds.waifu.WaifuSpawnEmbed;
import domain.exceptions.MyOwnException;
import ui.messages.MyMessage;
import domain.waifu.Waifu;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class WaifuSpawn extends MyMessage {

  private final Waifu waifu;

  public WaifuSpawn(Waifu newWaifu) {
    this.waifu = newWaifu;
  }

  @Override
  protected void startRoutine(Message message) throws MyOwnException {
    //Ignore.
  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
    return new WaifuSpawnEmbed(waifu);
  }
}
