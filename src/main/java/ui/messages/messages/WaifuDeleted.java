package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import domain.waifu.Waifu;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.messages.MyMessageAbs;

public class WaifuDeleted extends MyMessageAbs {

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
  public void startRoutine(Message message) throws MyOwnException {
    //Ignore.
  }

  @Override
  public EmbedBuilder getContent() throws MyOwnException {
    return this.convertStringToEmbed(
        player.getNameTag() + ", du hast " + waifu.getName() + " zerstoert. Du hast " + stardust
            + " Startdust und " + cookies + " Cookies erhalten.");
  }
}
