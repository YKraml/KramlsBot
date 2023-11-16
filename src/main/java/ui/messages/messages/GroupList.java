package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import logic.waifu.JikanFetcher;
import logic.waifu.PlayerLoader;
import logic.waifu.WaifuLoader;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.embeds.group.GroupsListEmbed;
import ui.messages.MessageSender;
import ui.messages.MyMessage;
import ui.reaction.GroupListListener;

public class GroupList extends MyMessage {

    private final Player player;
    private final PlayerLoader playerLoader;
    private final WaifuLoader waifuLoader;
    private final JikanFetcher jikanFetcher;
    private final MessageSender messageSender;

    public GroupList(Player player, PlayerLoader playerLoader, WaifuLoader waifuLoader,
                     JikanFetcher jikanFetcher, MessageSender messageSender) {
        this.player = player;
        this.playerLoader = playerLoader;
        this.waifuLoader = waifuLoader;
        this.jikanFetcher = jikanFetcher;
        this.messageSender = messageSender;
    }

    @Override
    protected void startRoutine(Message message) throws MyOwnException {
        this.addCountEmojis(message, player.getGroupList().size());
        message.addReactionAddListener(new GroupListListener(player, playerLoader, waifuLoader,
                jikanFetcher, messageSender));
    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        return new GroupsListEmbed(player, 0);
    }
}
