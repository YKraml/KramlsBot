package ui.messages.messages;

import domain.exceptions.MyOwnException;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.messages.MyMessageAbs;

public class IpMessage extends MyMessageAbs {

  private final String ip;

  public IpMessage(String ip) {
    this.ip = ip;
  }

  @Override
  public void startRoutine(Message message) throws MyOwnException {
    //Do nothing
  }

  @Override
  public EmbedBuilder getContent() throws MyOwnException {
    return convertStringToEmbed("Hier die IP-Adresse: " + ip);
  }
}
