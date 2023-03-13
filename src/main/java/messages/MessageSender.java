package messages;

import exceptions.MyOwnException;
import messages.messages.ExceptionHappenedMessage;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;

public interface MessageSender {

  Message send(MyMessage myMessage, TextChannel textChannel) throws MyOwnException;

  void sendSafe(MyMessage myMessage, TextChannel textChannel);
}
