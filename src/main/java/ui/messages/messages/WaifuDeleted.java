package ui.messages.messages;

import domain.exceptions.MyOwnException;
import ui.messages.MyMessage;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import domain.waifu.Player;
import domain.waifu.Waifu;

public class WaifuDeleted extends MyMessage {

  private final Player player;
  private final Waifu waifu;
  private final int stardust;
  private final int cookies;

  public WaifuDeleted(Player player, Waifu waifu, int stardust, int cookies) {
    this.player = player;
    this.waifu = waifu;
    this.stardust = stardust;
    this.cookies = cookies;
  }


  @Override
  protected void startRoutine(Message message) throws MyOwnException {
    //Ignore.
  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
    return this.convertStringToEmbed(
        player.getNameTag() + ", du hast " + waifu.getName() + " zerstoert. Du hast " + stardust
            + " Startdust und " + cookies + " Cookies erhalten.");
  }
}
