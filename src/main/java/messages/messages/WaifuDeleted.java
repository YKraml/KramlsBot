package messages.messages;

import exceptions.MyOwnException;
import messages.MyMessage;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import waifu.model.Player;
import waifu.model.Waifu;

public class WaifuDeleted extends MyMessage {

    private final Player player;
    private final Waifu waifu;
    private final int stardust;
    private final int cookies;

    public WaifuDeleted(Player player, Waifu waifu, int stardust, int cookies) {
        this.player = player;
        this.waifu = waifu;
        this.stardust = stardust;
        this.cookies = cookies;
    }


    @Override
    protected void startRoutine(Message message) throws MyOwnException {

    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        return this.convertStringToEmbed(player.getNameTag() + ", du hast " + waifu.getName() + " zerstoert. Du hast " + stardust + " Startdust und " + cookies + " Cookies erhalten.");
    }
}
