package embeds;

import exceptions.MyOwnException;
import messages.MyMessage;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import waifu.model.Player;

public class LostMoney extends MyMessage {

    private final Player player;
    private final long lostMoney;

    public LostMoney(Player player, long lostMoney) {
        this.player = player;
        this.lostMoney = lostMoney;
    }

    @Override
    protected void startRoutine(Message message) throws MyOwnException {

    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        return this.convertStringToEmbed(player.getNameTag() + ", du hast " + lostMoney + " Euro verloren. Du hast jetzt " + player.getInventory().getMoney() + " Euro");
    }
}
