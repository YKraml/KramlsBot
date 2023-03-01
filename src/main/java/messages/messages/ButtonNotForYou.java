package messages.messages;

import exceptions.MyOwnException;
import messages.MyMessage;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import waifu.model.Player;

public class ButtonNotForYou extends MyMessage {

  private final String triedPlayer;
  private final String forPlayer;

  public ButtonNotForYou(String triedPlayer, String forPlayer) {
    this.triedPlayer = triedPlayer;
    this.forPlayer = forPlayer;
  }


  @Override
  protected void startRoutine(Message message) throws MyOwnException {

  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
    return this.convertStringToEmbed("%s, der Knopf ist fuer %s".formatted(triedPlayer, forPlayer));
  }
}
