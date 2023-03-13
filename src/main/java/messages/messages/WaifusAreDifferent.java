package messages.messages;

import exceptions.MyOwnException;
import messages.MyMessage;
import waifu.model.Player;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class WaifusAreDifferent extends MyMessage {

  private final Player player;

  public WaifusAreDifferent(Player player) {
    this.player = player;
  }

  @Override
  protected void startRoutine(Message message) throws MyOwnException {
    //Ignore.
  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
    return this.convertStringToEmbed(player.getNameTag() + ", die zwei Waifus sind nicht gleich");
  }
}
