package messages.messages;

import exceptions.MyOwnException;
import messages.MyMessage;
import waifu.model.Player;
import waifu.model.Waifu;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class Merged extends MyMessage {

  private final Player player;
  private final Waifu waifu1;

  public Merged(Player player, Waifu waifu) {
    this.player = player;
    this.waifu1 = waifu;
  }


  @Override
  protected void startRoutine(Message message) throws MyOwnException {
    //Ignore.
  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
    return this.convertStringToEmbed(
        player.getNameTag() + ", du hast zwei Waifus verbunden. \"" + waifu1.getName()
            + "\" hat nun das Sternenlevel " + waifu1.getStarLevel());
  }
}
