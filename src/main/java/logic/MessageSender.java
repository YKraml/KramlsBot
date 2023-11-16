package logic;

import domain.exceptions.MyOwnException;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import ui.messages.MyMessage;

public interface MessageSender {

    Message send(MyMessage myMessage, TextChannel textChannel) throws MyOwnException;

    void sendSafe(MyMessage myMessage, TextChannel textChannel);
}
