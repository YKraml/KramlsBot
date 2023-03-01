package messages;

import exceptions.MyOwnException;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;

public interface MessageSender {

  Message send(MyMessage myMessage, TextChannel serverTextChannel) throws MyOwnException;
}
