package messages.messages;

import exceptions.MyOwnException;
import messages.MyMessage;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import waifu.model.Player;

public class ChangedPicture extends MyMessage {

  private final Player player;
  private final long cost;

  public ChangedPicture(Player player, long cost) {
    this.player = player;
    this.cost = cost;
  }

  @Override
  protected void startRoutine(Message message) throws MyOwnException {

  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
    return convertStringToEmbed(
        "%s, das Bild wurde gewechselt. Es hat dich %d Morphstein(e) gekostet.".formatted(player.getNameTag(),
            cost));
  }
}
