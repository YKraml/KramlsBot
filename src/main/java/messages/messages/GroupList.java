package messages.messages;

import actions.listeners.reaction.lists.GroupListListener;
import embeds.group.GroupsListEmbed;
import exceptions.MyOwnException;
import messages.MessageSender;
import messages.MyMessage;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import waifu.JikanFetcher;
import waifu.loader.PlayerLoader;
import waifu.loader.WaifuLoader;
import waifu.model.Player;

public class GroupList extends MyMessage {
    private final Player player;
    private final PlayerLoader playerLoader;
    private final WaifuLoader waifuLoader;
    private final JikanFetcher jikanFetcher;
    private final MessageSender messageSender;

    public GroupList(Player player, PlayerLoader playerLoader, WaifuLoader waifuLoader,
        JikanFetcher jikanFetcher, MessageSender messageSender) {
        super();
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
