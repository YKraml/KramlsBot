package messages.messages;

import actions.listeners.reaction.lists.WaifuListListener;
import discord.Emojis;
import embeds.waifu.WaifuListEmbed;
import exceptions.MyOwnException;
import messages.MessageSender;
import messages.MyMessage;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import waifu.JikanFetcher;
import waifu.loader.PlayerLoader;
import waifu.loader.WaifuLoader;
import waifu.model.Player;

public class WaifuList extends MyMessage {

    private final Player player;
    private final MessageSender messageSender;
    private final PlayerLoader playerLoader;
    private final WaifuLoader waifuLoader;
    private final JikanFetcher jikanFetcher;

    public WaifuList(Player player, MessageSender messageSender, PlayerLoader playerLoader,
        WaifuLoader waifuLoader, JikanFetcher jikanFetcher) {
        super();
        this.player = player;
        this.messageSender = messageSender;
        this.playerLoader = playerLoader;
        this.waifuLoader = waifuLoader;
        this.jikanFetcher = jikanFetcher;
    }

    @Override
    protected void startRoutine(Message message) throws MyOwnException {
        this.addCountEmojis(message, player.getWaifuList().size());
        message.addReaction(Emojis.COOKIE.getEmoji());
        message.addReaction(Emojis.STAR2.getEmoji());
        message.addReaction(Emojis.ABC.getEmoji());
        message.addReactionAddListener(new WaifuListListener(player, messageSender, playerLoader,
            waifuLoader, jikanFetcher));
    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        return new WaifuListEmbed(player, 0);
    }
}
