package messages.messages;

import exceptions.MyOwnException;
import messages.MyMessage;
import waifu.model.Player;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class GuessedRight extends MyMessage {

  private final Player player;

  public GuessedRight(Player player) {
    super();
    this.player = player;
  }

  @Override
  protected void startRoutine(Message message) throws MyOwnException {
    //Ignore.
  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
    return this.convertStringToEmbed(player.getNameTag() + ", du liegst richtig!");
  }
}
