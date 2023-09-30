package messages.messages;

import exceptions.MyOwnException;
import messages.MyMessage;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class IpMessage extends MyMessage {

  private final String ip;

  public IpMessage(String ip) {
    this.ip = ip;
  }

  @Override
  protected void startRoutine(Message message) throws MyOwnException {
    //Do nothing
  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
    return convertStringToEmbed("Hier die IP-Adresse: " + ip);
  }
}
