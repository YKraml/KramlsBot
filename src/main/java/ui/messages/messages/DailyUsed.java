package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.messages.MyMessageAbs;

public class DailyUsed extends MyMessageAbs {

    private final Player player;

    public DailyUsed(Player player) {
        this.player = player;
    }


    @Override
    public void startRoutine(Message message) throws MyOwnException {
        //Ignore.
    }

    @Override
    public EmbedBuilder getContent() throws MyOwnException {
        return convertStringToEmbed(
                player.getNameTag() + ", du hast 1000 Euro, 1 Cookie und 100 Stardust erhalten.");
    }
}
