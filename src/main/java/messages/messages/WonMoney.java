package messages.messages;

import exceptions.MyOwnException;
import messages.MyMessage;
import waifu.model.Player;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class WonMoney extends MyMessage {

    private final Player player;
    private final int wonMoney;

    public WonMoney(Player player, int wonMoney) {
        this.player = player;
        this.wonMoney = wonMoney;
    }


    @Override
    protected void startRoutine(Message message) throws MyOwnException {

    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        return this.convertStringToEmbed(player.getNameTag() + ", du hast " + wonMoney + " Euro gewonnen. Du hast jetzt " + player.getInventory().getMoney() + " Euro");
    }

}
