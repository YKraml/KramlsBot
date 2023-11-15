package ui.messages.messages;

import ui.embeds.ExceptionEmbed;
import domain.exceptions.MyOwnException;
import ui.messages.MyMessage;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class ExceptionHappenedMessage extends MyMessage {

  private final MyOwnException myOwnException;

  public ExceptionHappenedMessage(MyOwnException myOwnException) {
    this.myOwnException = myOwnException;
  }

  @Override
  protected void startRoutine(Message message) throws MyOwnException {
    //Ignore.
  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
    return new ExceptionEmbed(myOwnException);
  }
}
