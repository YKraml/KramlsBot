package logic.routines;

import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import domain.waifu.Waifu;
import logic.waifu.PlayerLoader;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;
import domain.Answer;
import ui.messages.MessageSender;
import ui.messages.messages.GaveWaifu;
import ui.messages.messages.WaifuNotFound;

public class RoutineGiveWaifu extends Routine {

    private final User receiverUser;
    private final User senderUser;
    private final int waifuNumber;
    private final TextChannel channel;
    private final PlayerLoader playerLoader;
    private final MessageSender messageSender;

    public RoutineGiveWaifu(TextChannel channel, User senderUser, User receiverUser, int waifuNumber,
                            PlayerLoader playerLoader, MessageSender messageSender) {
        this.channel = channel;
        this.senderUser = senderUser;
        this.receiverUser = receiverUser;
        this.waifuNumber = waifuNumber;
        this.playerLoader = playerLoader;
        this.messageSender = messageSender;
    }

    @Override
    Answer start(RoutineRunner routineRunner) throws MyOwnException {

        Player senderPlayer = playerLoader.getPlayerByUser(senderUser);
        Player receiverPlayer = playerLoader.getPlayerByUser(receiverUser);
        if (waifuNumber <= senderPlayer.getWaifuList().size()) {

            Waifu waifu = senderPlayer.getWaifuList().get(waifuNumber);
            receiverPlayer.addWaifu(waifu);
            senderPlayer.deleteWaifu(waifu);

            playerLoader.savePlayer(receiverPlayer);
            playerLoader.savePlayer(senderPlayer);

            messageSender.send(new GaveWaifu(senderPlayer, receiverPlayer, waifu), channel);
        } else {
            messageSender.send(new WaifuNotFound(waifuNumber), channel);
        }

        return new Answer("Someone gave a Waifu away to '%s'".formatted(receiverUser));
    }

}
