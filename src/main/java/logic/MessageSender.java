package logic;

import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import domain.waifu.Waifu;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;

public interface MessageSender {

    Message send(MyMessage myMessage, TextChannel textChannel) throws MyOwnException;

    void sendSafe(MyMessage myMessage, TextChannel textChannel);

    void sendMerged(TextChannel channel, Player player, Waifu waifu) throws MyOwnException;

    void sendWaifusAreDifferent(TextChannel channel, Player player) throws MyOwnException;
}
