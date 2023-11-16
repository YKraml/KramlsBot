package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.messages.MyMessage;

public class ChangedPicture extends MyMessage {

    private final Player player;
    private final long cost;

    public ChangedPicture(Player player, long cost) {
        this.player = player;
        this.cost = cost;
    }

    @Override
    protected void startRoutine(Message message) throws MyOwnException {
        //Just ignore.
    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        return convertStringToEmbed(
                "%s, das Bild wurde gewechselt. Es hat dich %d Morphstein(e) gekostet.".formatted(
                        player.getNameTag(),
                        cost));
    }
}
