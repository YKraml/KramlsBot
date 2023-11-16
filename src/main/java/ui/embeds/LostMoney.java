package ui.embeds;

import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.messages.MyMessageAbs;

public class LostMoney extends MyMessageAbs {

    private final Player player;
    private final long lostMoney;

    public LostMoney(Player player, long lostMoney) {
        this.player = player;
        this.lostMoney = lostMoney;
    }

    @Override
    public void startRoutine(Message message) throws MyOwnException {
        //Just ignore.
    }

    @Override
    public EmbedBuilder getContent() throws MyOwnException {
        return this.convertStringToEmbed(
                player.getNameTag() + ", du hast " + lostMoney + " Euro verloren. Du hast jetzt "
                        + player.getInventory().getMoney() + " Euro");
    }
}
