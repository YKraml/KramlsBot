package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.messages.MyMessageAbs;

public class ExpandedList extends MyMessageAbs {

  private final Player player;
  private final int cost;

  public ExpandedList(Player player, int costForUpgrade) {
    this.cost = costForUpgrade;
    this.player = player;
  }


  @Override
  public void startRoutine(Message message) throws MyOwnException {
    //Ignore.
  }

  @Override
  public EmbedBuilder getContent() throws MyOwnException {
    return this.convertStringToEmbed(
        "%s, du kannst jetzt Waifus %d lagern. Das Upgrade hat dich %d Euro gekostet.".formatted(
            player.getNameTag(), player.getMaxWaifus(), cost));
  }

}
