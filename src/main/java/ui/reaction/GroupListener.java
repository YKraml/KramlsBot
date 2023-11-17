package ui.reaction;

import domain.exceptions.MyOwnException;
import domain.waifu.Group;
import domain.waifu.Player;
import domain.waifu.Waifu;
import logic.messages.MessageSender;
import logic.waifu.JikanFetcher;
import domain.PlayerLoader;
import domain.WaifuLoader;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.listener.message.reaction.ReactionAddListener;
import ui.embeds.group.GroupEmbed;
import ui.messages.messages.WaifuNotFound;
import ui.messages.messages.WaifuStats;

import java.util.ArrayList;

public class GroupListener extends MyAbstractListListener<Waifu> implements ReactionAddListener {

    private final Group group;
    private final Player player;
    private final PlayerLoader playerLoader;
    private final WaifuLoader waifuLoader;
    private final JikanFetcher jikanFetcher;
    private final MessageSender messageSender;

    public GroupListener(Group group, Player player, PlayerLoader playerLoader,
                         WaifuLoader waifuLoader, JikanFetcher jikanFetcher, MessageSender messageSender) {
        super(new ArrayList<>(group.getWaifuList()));
        this.group = group;
        this.player = player;
        this.playerLoader = playerLoader;
        this.waifuLoader = waifuLoader;
        this.jikanFetcher = jikanFetcher;
        this.messageSender = messageSender;
    }

    @Override
    protected void updateMessage(Message message, int page) throws MyOwnException {
        message.edit(new GroupEmbed(group, player, page));
    }

    @Override
    protected void reactToCountEmoji(Server server, TextChannel textChannel, User user,
                                     int listPosition)
            throws MyOwnException {
        Waifu waifu = group.getWaifuList().get(listPosition);
        messageSender.send(new WaifuStats(waifu, player, playerLoader,
                waifuLoader, jikanFetcher, messageSender), textChannel);
    }

    @Override
    protected void reactToTooHighCountEmoji(TextChannel textChannel, int listPosition)
            throws MyOwnException {
        messageSender.send(new WaifuNotFound(listPosition), textChannel);
    }
}
