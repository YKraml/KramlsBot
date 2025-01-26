package ui.messages.messages;

import domain.exceptions.MyOwnException;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.messages.MyMessageAbs;

public class ButtonNotForYou extends MyMessageAbs {

  private final String triedPlayer;
  private final String forPlayer;

  public ButtonNotForYou(String triedPlayer, String forPlayer) {
    this.triedPlayer = triedPlayer;
    this.forPlayer = forPlayer;
  }


  @Override
  public void startRoutine(Message message) throws MyOwnException {
    //Just ignore.
  }

  @Override
  public EmbedBuilder getContent() throws MyOwnException {
    return this.convertStringToEmbed("%s, der Knopf ist fuer %s".formatted(triedPlayer, forPlayer));
  }
}
