package ui.messages.messages;

import ui.messages.MessageSender;
import ui.messages.MyMessage;
import ui.reaction.DeleteWaifuListListener;
import ui.embeds.waifu.DeleteWaifuListEmbed;
import domain.exceptions.MyOwnException;
import util.Emojis;
import org.javacord.api.entity.user.User;
import logic.waifu.PlayerLoader;
import logic.waifu.WaifuLoader;
import domain.waifu.Player;
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