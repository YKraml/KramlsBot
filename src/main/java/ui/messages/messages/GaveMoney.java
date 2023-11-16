package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.messages.MyMessage;

public class GaveMoney extends MyMessage {

    private final Player player1;
    private final Player player2;
    private final int money;

    public GaveMoney(Player player1, Player player2, int money) {
        this.player1 = player1;
        this.player2 = player2;
        this.money = money;
    }


    @Override
    protected void startRoutine(Message message) throws MyOwnException {
        //Ignore.
    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        return this.convertStringToEmbed(
                player1.getNameTag() + " gab " + player2.getNameTag() + " " + money + " Euro.");
    }

}
