package messages.messages;

import exceptions.MyOwnException;
import messages.MyMessage;
import waifu.model.Waifu;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class WaifuToClaimWas extends MyMessage {

    private final Waifu waifu;
    public WaifuToClaimWas(Waifu waifu) {
        super();
        this.waifu = waifu;
    }

    @Override
    protected void startRoutine(Message message) throws MyOwnException {

    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        return this.convertStringToEmbed("Die Waifu hiess \"" + waifu.getName() + "\" von \"" + waifu.getAnimeName() + "\"");
    }
}
