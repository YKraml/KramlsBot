package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.messages.MyMessage;

public class WaifuSpawned extends MyMessage {

    private final Player player;

    public WaifuSpawned(Player player) {
        this.player = player;
    }

    @Override
    protected void startRoutine(Message message) throws MyOwnException {
        //Ignore.
    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        return this.convertStringToEmbed(player.getNameTag() + ", du hast eine neue Waifu erhalten:");
    }
}
