package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.messages.MyMessageAbs;

public class WaifusDeleted extends MyMessageAbs {

  private final Player player;
  private final int deletedWaifus;
  private final int stardust;
  private final int cookies;

  public WaifusDeleted(Player player, int deletedWaifus, int stardust, int cookies) {
    this.player = player;
    this.deletedWaifus = deletedWaifus;
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
        player.getNameTag() + ", du hast " + deletedWaifus + " Waifus zerstoert. Du hast "
            + stardust + " Startdust und " + cookies + " Cookies erhalten.");
  }

}
