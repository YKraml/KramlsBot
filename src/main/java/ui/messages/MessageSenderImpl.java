package ui.messages;

import domain.exceptions.MyOwnException;
import domain.exceptions.messages.CouldNotGetContent;
import domain.exceptions.messages.CouldNotSendMessage;
import domain.exceptions.messages.CouldNotStartRoutine;
import domain.waifu.Player;
import domain.waifu.Waifu;
import logic.MessageSender;
import logic.MyMessage;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.messages.messages.Merged;
import ui.messages.messages.WaifusAreDifferent;

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
            throw new MyOwnException(new CouldNotSendMessage(myMessage.getClass().getSimpleName()), null);
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

    @Override
    public void sendMerged(TextChannel channel, Player player, Waifu waifu) throws MyOwnException {
        send(new Merged(player, waifu), channel);
    }

    @Override
    public void sendWaifusAreDifferent(TextChannel channel, Player player) throws MyOwnException {
        send(new WaifusAreDifferent(player), channel);
    }


}
