package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.messages.MyMessageAbs;

public class WonMoney extends MyMessageAbs {

  private final Player player;
  private final long wonMoney;

  public WonMoney(Player player, long wonMoney) {
    this.player = player;
    this.wonMoney = wonMoney;
  }


  @Override
  public void startRoutine(Message message) throws MyOwnException {
    //Ignore.
  }

  @Override
  public EmbedBuilder getContent() throws MyOwnException {
    return this.convertStringToEmbed(
        "%s, du hast %d Euro gewonnen. Du hast jetzt %d Euro".formatted(player.getNameTag(),
            wonMoney, player.getInventory().getMoney()));
  }

}
