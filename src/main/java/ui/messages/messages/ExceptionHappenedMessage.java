package ui.messages.messages;

import domain.exceptions.MyOwnException;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.embeds.ExceptionEmbed;
import ui.messages.MyMessageAbs;

public class ExceptionHappenedMessage extends MyMessageAbs {

  private final MyOwnException myOwnException;

  public ExceptionHappenedMessage(MyOwnException myOwnException) {
    this.myOwnException = myOwnException;
  }

  @Override
  public void startRoutine(Message message) throws MyOwnException {
    //Ignore.
  }

  @Override
  public EmbedBuilder getContent() throws MyOwnException {
    return new ExceptionEmbed(myOwnException);
  }
}
