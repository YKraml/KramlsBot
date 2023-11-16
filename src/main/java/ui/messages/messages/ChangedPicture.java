package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.messages.MyMessageAbs;

public class ChangedPicture extends MyMessageAbs {

    private final Player player;
    private final long cost;

    public ChangedPicture(Player player, long cost) {
        this.player = player;
        this.cost = cost;
    }

    @Override
    public void startRoutine(Message message) throws MyOwnException {
        //Just ignore.
    }

    @Override
    public EmbedBuilder getContent() throws MyOwnException {
        return convertStringToEmbed(
                "%s, das Bild wurde gewechselt. Es hat dich %d Morphstein(e) gekostet.".formatted(
                        player.getNameTag(),
                        cost));
    }
}
