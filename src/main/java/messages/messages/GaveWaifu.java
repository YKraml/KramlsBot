package messages.messages;

import exceptions.MyOwnException;
import messages.MyMessage;
import waifu.model.Player;
import waifu.model.Waifu;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class GaveWaifu extends MyMessage {
    private final Player player1;
    private final Player player2;
    private  final Waifu waifu;

    public GaveWaifu(Player player1, Player player2, Waifu waifu) {
        this.player1 = player1;
        this.player2 = player2;
        this.waifu = waifu;
    }

    @Override
    protected void startRoutine(Message message) throws MyOwnException {

    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        return this.convertStringToEmbed(player1.getNameTag() + " gab " + player2.getNameTag() + " \"" + waifu.getName() + "\"");
    }
}
