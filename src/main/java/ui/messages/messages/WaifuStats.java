package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import domain.waifu.Waifu;
import logic.waifu.JikanFetcher;
import logic.waifu.PlayerLoader;
import logic.waifu.WaifuLoader;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.embeds.waifu.WaifuEmbed;
import ui.messages.MessageSender;
import ui.messages.MyMessage;
import ui.reaction.WaifuEditListener;
import util.Emojis;

public class WaifuStats extends MyMessage {

    private final Player player;
    private final Waifu waifu;
    private final PlayerLoader playerLoader;
    private final WaifuLoader waifuLoader;
    private final JikanFetcher jikanFetcher;
    private final MessageSender messageSender;

    public WaifuStats(Waifu waifu, Player player, PlayerLoader playerLoader, WaifuLoader waifuLoader,
                      JikanFetcher jikanFetcher, MessageSender messageSender) {
        this.player = player;
        this.waifu = waifu;
        this.playerLoader = playerLoader;
        this.waifuLoader = waifuLoader;
        this.jikanFetcher = jikanFetcher;
        this.messageSender = messageSender;
    }

    @Override
    protected void startRoutine(Message message) throws MyOwnException {
        message.addReaction(Emojis.COOKIE.getEmoji());
        message.addReaction(Emojis.CUPCAKE.getEmoji());
        message.addReaction(Emojis.STAR2.getEmoji());
        message.addReaction(Emojis.HAT.getEmoji());
        message.addReaction(Emojis.X.getEmoji());
        message.addReactionAddListener(new WaifuEditListener(waifu, player, playerLoader, waifuLoader,
                jikanFetcher, messageSender));
    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        return new WaifuEmbed(waifu);
    }
}
