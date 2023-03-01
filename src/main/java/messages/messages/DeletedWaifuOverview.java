package messages.messages;

import actions.listeners.reaction.lists.DeleteWaifuListListener;
import embeds.waifu.DeleteWaifuListEmbed;
import exceptions.MyOwnException;
import messages.MessageSender;
import messages.MyMessage;
import discord.Emojis;
import waifu.loader.WaifuLoader;
import waifu.model.Player;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class DeletedWaifuOverview extends MyMessage {

    private final Player player;
    private final WaifuLoader waifuLoader;
    private final MessageSender messageSender;

    public DeletedWaifuOverview(Player player, WaifuLoader waifuLoader, MessageSender messageSender) {
        super();
        this.player = player;
        this.waifuLoader = waifuLoader;
        this.messageSender = messageSender;
    }

    @Override
    protected void startRoutine(Message message) throws MyOwnException {
        this.addCountEmojis(message, player.getWaifuList().size());
        message.addReactionAddListener(new DeleteWaifuListListener(player, waifuLoader,
            messageSender));
        message.addReaction(Emojis.COOKIE.getEmoji());
        message.addReaction(Emojis.STAR2.getEmoji());
        message.addReaction(Emojis.ABC.getEmoji());
    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        return new DeleteWaifuListEmbed(player, 0);
    }

}
