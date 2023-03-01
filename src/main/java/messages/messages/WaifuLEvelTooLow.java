package messages.messages;

import exceptions.MyOwnException;
import messages.MyMessage;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import waifu.model.Player;
import waifu.model.Waifu;

public class WaifuLEvelTooLow extends MyMessage {
    private final Player player;
    private final Waifu waifu;

    public WaifuLEvelTooLow(Player player, Waifu waifu) {
        super();
        this.player = player;
        this.waifu = waifu;
    }

    @Override
    protected void startRoutine(Message message) throws MyOwnException {

    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        return this.convertStringToEmbed(player.getNameTag() + ", das level von \"" + waifu.getName() + "\" muss mindestens " + waifu.getRarity().getNextRarity().getMinLevel() + " sein.");
    }
}
