package actions.listeners.reaction.lists;

import actions.listeners.reaction.util.MyAbstractListListener;
import embeds.group.GroupsListEmbed;
import exceptions.MyOwnException;
import messages.MessageSender;
import messages.MessageSenderImpl;
import messages.messages.GroupNotFound;
import messages.messages.GroupOverview;
import waifu.JikanFetcher;
import waifu.loader.PlayerLoader;
import waifu.loader.WaifuLoader;
import waifu.model.Group;
import waifu.model.Player;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;

public class GroupListListener extends MyAbstractListListener<Group> {

    private final Player player;
    private final PlayerLoader playerLoader;
    private final WaifuLoader waifuLoader;
    private final JikanFetcher jikanFetcher;
    private final MessageSender messageSender;

    public GroupListListener(Player player, PlayerLoader playerLoader, WaifuLoader waifuLoader,
        JikanFetcher jikanFetcher, MessageSender messageSender) {
        super(player.getGroupList());
        this.player = player;
        this.playerLoader = playerLoader;
        this.waifuLoader = waifuLoader;
        this.jikanFetcher = jikanFetcher;
        this.messageSender = messageSender;
    }

    @Override
    protected void updateMessage(Message message, int page) {
        message.edit(new GroupsListEmbed(player, page));
    }

    @Override
    protected void reactToCountEmoji(TextChannel textChannel, int listPosition) throws MyOwnException {
        Group group = player.getGroupList().get(listPosition);
        MessageSenderImpl result;
        synchronized (MessageSenderImpl.class) {
            result = new MessageSenderImpl();
        }
        result.send(new GroupOverview(group, player, playerLoader,
            waifuLoader, jikanFetcher, messageSender), textChannel);
    }

    @Override
    protected void reactToTooHighCountEmoji(TextChannel textChannel, int listPosition) throws MyOwnException {
        MessageSenderImpl result;
        synchronized (MessageSenderImpl.class) {
            result = new MessageSenderImpl();
        }
        result.send(new GroupNotFound(), textChannel);
    }

}
