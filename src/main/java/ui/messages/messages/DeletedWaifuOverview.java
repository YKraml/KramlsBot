package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import logic.waifu.PlayerLoader;
import logic.waifu.WaifuLoader;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import ui.embeds.waifu.DeleteWaifuListEmbed;
import logic.MessageSender;
import ui.messages.MyMessageAbs;
import ui.reaction.DeleteWaifuListListener;
import util.Emojis;

public class DeletedWaifuOverview extends MyMessageAbs {

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
    public void startRoutine(Message message) throws MyOwnException {
        Player player = playerLoader.getPlayerByUser(user);
        this.addCountEmojis(message, player.getWaifuList().size());
        message.addReactionAddListener(new DeleteWaifuListListener(player, waifuLoader,
                messageSender));
        message.addReaction(Emojis.COOKIE.getEmoji());
        message.addReaction(Emojis.STAR2.getEmoji());
        message.addReaction(Emojis.ABC.getEmoji());
    }

    @Override
    public EmbedBuilder getContent() throws MyOwnException {
        Player player = playerLoader.getPlayerByUser(user);
        return new DeleteWaifuListEmbed(player, 0);
    }

}
