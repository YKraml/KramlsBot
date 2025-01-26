package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import domain.waifu.Waifu;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.messages.MyMessageAbs;

public class WaifuLevelTooLow extends MyMessageAbs {

  private final Player player;
  private final Waifu waifu;

  public WaifuLevelTooLow(Player player, Waifu waifu) {
    this.player = player;
    this.waifu = waifu;
  }

  @Override
  public void startRoutine(Message message) throws MyOwnException {
    //Ignore.
  }

  @Override
  public EmbedBuilder getContent() throws MyOwnException {
    return this.convertStringToEmbed(
        player.getNameTag() + ", das level von \"" + waifu.getName() + "\" muss mindestens "
            + waifu.getRarity().getNextRarity().getMinLevel() + " sein.");
  }
}
