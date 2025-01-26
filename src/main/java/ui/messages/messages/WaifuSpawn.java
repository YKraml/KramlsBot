package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.waifu.Waifu;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.embeds.waifu.WaifuSpawnEmbed;
import ui.messages.MyMessageAbs;

public class WaifuSpawn extends MyMessageAbs {

  private final Waifu waifu;

  public WaifuSpawn(Waifu newWaifu) {
    this.waifu = newWaifu;
  }

  @Override
  public void startRoutine(Message message) throws MyOwnException {
    //Ignore.
  }

  @Override
  public EmbedBuilder getContent() throws MyOwnException {
    return new WaifuSpawnEmbed(waifu);
  }
}
