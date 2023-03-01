package messages.messages;

import exceptions.MyOwnException;
import messages.MyMessage;
import waifu.model.Player;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class DailyUsed extends MyMessage {
    private final Player player;

    public DailyUsed(Player player) {
        this.player = player;
    }


    @Override
    protected void startRoutine(Message message) throws MyOwnException {

    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        return convertStringToEmbed(player.getNameTag() + ", du hast 1000 Euro, 1 Cookie und 100 Stardust erhalten.");
    }
}
