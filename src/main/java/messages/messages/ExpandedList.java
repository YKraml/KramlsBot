package messages.messages;

import exceptions.MyOwnException;
import messages.MyMessage;
import waifu.model.Player;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class ExpandedList extends MyMessage {

  private final Player player;
  private final int cost;

  public ExpandedList(Player player, int costForUpgrade) {
    this.cost = costForUpgrade;
    this.player = player;
  }


  @Override
  protected void startRoutine(Message message) throws MyOwnException {
    //Ignore.
  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
    return this.convertStringToEmbed(
        "%s, du kannst jetzt Waifus %d lagern. Das Upgrade hat dich %d Euro gekostet.".formatted(
            player.getNameTag(), player.getMaxWaifus(), cost));
  }

}
