package messages.messages;

import exceptions.MyOwnException;
import messages.MyMessage;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class CommandNotFound extends MyMessage {

  private final String command;

  public CommandNotFound(String command) {
    super();
    this.command = command;
  }

  @Override
  protected void startRoutine(Message message) throws MyOwnException {

  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
    return this.convertStringToEmbed("Konnte den Befehl '%s' nicht finden".formatted(command) );
  }
}
