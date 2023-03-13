package messages;

import exceptions.MyOwnException;
import exceptions.messages.CouldNotGetContent;
import exceptions.messages.CouldNotSendMessage;
import exceptions.messages.CouldNotStartRoutine;
import java.util.function.BiConsumer;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.util.concurrent.CompletableFuture;

public class MessageSenderImpl implements MessageSender {


  @Override
  public Message send(MyMessage myMessage, TextChannel textChannel) throws MyOwnException {

    EmbedBuilder content;
    try {
      content = myMessage.getContent();
    } catch (MyOwnException e) {
      throw new MyOwnException(new CouldNotGetContent(), e);
    }

    if (!textChannel.canYouWrite()) {
      throw new MyOwnException(new CouldNotSendMessage(myMessage), null);
    }

    CompletableFuture<Message> sentMessageFuture = textChannel.sendMessage(content);
    Message sentMessage = sentMessageFuture.join();

    try {
      myMessage.startRoutine(sentMessage);
    } catch (MyOwnException e) {
      throw new MyOwnException(new CouldNotStartRoutine(), e);
    }

    return sentMessage;
  }

  @Override
  public void sendSafe(MyMessage myMessage, TextChannel textChannel) {

    try {
      CompletableFuture<Message> future = textChannel.sendMessage(myMessage.getContent());
      myMessage.startRoutine(future.join());
    } catch (MyOwnException ignored) {
      //Ignore
    }


  }


}
