package logic.messages;

import domain.exceptions.MyOwnException;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public interface MyMessage {

  void startRoutine(Message message) throws MyOwnException;

  EmbedBuilder getContent() throws MyOwnException;
}
