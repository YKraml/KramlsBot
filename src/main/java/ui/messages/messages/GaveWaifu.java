package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import domain.waifu.Waifu;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.messages.MyMessageAbs;

public class GaveWaifu extends MyMessageAbs {

    private final Player player1;
    private final Player player2;
    private final Waifu waifu;

    public GaveWaifu(Player player1, Player player2, Waifu waifu) {
        this.player1 = player1;
        this.player2 = player2;
        this.waifu = waifu;
    }

    @Override
    public void startRoutine(Message message) throws MyOwnException {
        //Ignore.
    }

    @Override
    public EmbedBuilder getContent() throws MyOwnException {
        return this.convertStringToEmbed(
                player1.getNameTag() + " gab " + player2.getNameTag() + " \"" + waifu.getName() + "\"");
    }
}
