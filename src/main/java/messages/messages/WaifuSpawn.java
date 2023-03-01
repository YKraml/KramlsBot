package messages.messages;

import embeds.waifu.WaifuSpawnEmbed;
import exceptions.MyOwnException;
import messages.MyMessage;
import waifu.model.Waifu;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class WaifuSpawn extends MyMessage {

    private final Waifu waifu;

    public WaifuSpawn(Waifu newWaifu) {
        super();
        this.waifu = newWaifu;
    }

    @Override
    protected void startRoutine(Message message) throws MyOwnException {

    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        return new WaifuSpawnEmbed(waifu);
    }
}
