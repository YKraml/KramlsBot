package messages.messages;

import exceptions.MyOwnException;
import messages.MyMessage;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import waifu.model.Player;

public class NotEnoughStardust extends MyMessage {

    private final Player player;
    private final int neededStardust;

    public NotEnoughStardust(Player player, int neededStardust) {
        super();
        this.player = player;
        this.neededStardust = neededStardust;
    }

    @Override
    protected void startRoutine(Message message) throws MyOwnException {

    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        return this.convertStringToEmbed(player.getNameTag() + ", du hast nicht genuegend Stardust, du brauchst " + neededStardust + ".");
    }
}
