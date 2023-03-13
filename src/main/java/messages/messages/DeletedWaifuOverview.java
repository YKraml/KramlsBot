package messages.messages;

import actions.listeners.reaction.DeleteWaifuListListener;
import embeds.waifu.DeleteWaifuListEmbed;
import exceptions.MyOwnException;
import messages.MessageSender;
import messages.MyMessage;
import discord.Emojis;
import org.javacord.api.entity.user.User;
import waifu.loader.PlayerLoader;
import waifu.loader.WaifuLoader;
import waifu.model.Player;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class DeletedWaifuOverview extends MyMessage {

    private final User user;
    private final WaifuLoader waifuLoader;
    private final MessageSender messageSender;
    private final PlayerLoader playerLoader;

    public DeletedWaifuOverview(User user, WaifuLoader waifuLoader, MessageSender messageSender,
        PlayerLoader playerLoader) {
        this.user = user;
        this.waifuLoader = waifuLoader;
        this.messageSender = messageSender;
        this.playerLoader = playerLoader;
    }

    @Override
    protected void startRoutine(Message message) throws MyOwnException {
        Player player = playerLoader.getPlayerByUser(user);
        this.addCountEmojis(message, player.getWaifuList().size());
        message.addReactionAddListener(new DeleteWaifuListListener(player, waifuLoader,
            messageSender));
        message.addReaction(Emojis.COOKIE.getEmoji());
        message.addReaction(Emojis.STAR2.getEmoji());
        message.addReaction(Emojis.ABC.getEmoji());
    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        Player player = playerLoader.getPlayerByUser(user);
        return new DeleteWaifuListEmbed(player, 0);
    }

}
