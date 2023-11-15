package ui.messages.messages;

import domain.exceptions.MyOwnException;
import ui.messages.MyMessage;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import domain.waifu.Player;
import domain.waifu.Waifu;

public class WaifuLevelTooLow extends MyMessage {

  private final Player player;
  private final Waifu waifu;

  public WaifuLevelTooLow(Player player, Waifu waifu) {
    this.player = player;
    this.waifu = waifu;
  }

  @Override
  protected void startRoutine(Message message) throws MyOwnException {
    //Ignore.
  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
    return this.convertStringToEmbed(
        player.getNameTag() + ", das level von \"" + waifu.getName() + "\" muss mindestens "
            + waifu.getRarity().getNextRarity().getMinLevel() + " sein.");
  }
}
