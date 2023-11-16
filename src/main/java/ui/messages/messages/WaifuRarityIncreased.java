package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.waifu.Rarities;
import domain.waifu.Waifu;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.messages.MyMessage;

public class WaifuRarityIncreased extends MyMessage {

    private final Waifu waifu;
    private final Rarities rarity;

    public WaifuRarityIncreased(Waifu waifu, Rarities rarity) {
        this.waifu = waifu;
        this.rarity = rarity;
    }

    @Override
    protected void startRoutine(Message message) throws MyOwnException {
        //Ignore.
    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        return this.convertStringToEmbed(waifu.getName() + " ist nun " + rarity + ".");
    }
}
