package messages.messages;

import actions.listeners.reaction.GroupListener;
import embeds.group.GroupEmbed;
import exceptions.MyOwnException;
import messages.MessageSender;
import messages.MyMessage;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import waifu.JikanFetcher;
import waifu.loader.PlayerLoader;
import waifu.loader.WaifuLoader;
import waifu.model.Group;
import waifu.model.Player;

public class GroupOverview extends MyMessage {

    private final Group group;
    private final Player player;
    private final PlayerLoader playerLoader;
    private final WaifuLoader waifuLoader;
    private final JikanFetcher jikanFetcher;
    private final MessageSender messageSender;

    public GroupOverview(Group group, Player player, PlayerLoader playerLoader,
        WaifuLoader waifuLoader, JikanFetcher jikanFetcher, MessageSender messageSender) {
        this.group = group;
        this.player = player;
        this.playerLoader = playerLoader;
        this.waifuLoader = waifuLoader;
        this.jikanFetcher = jikanFetcher;
        this.messageSender = messageSender;
    }

    @Override
    protected void startRoutine(Message message) throws MyOwnException {
        this.addCountEmojis(message, group.getWaifuSet().size());
        message.addReactionAddListener(new GroupListener(group, player, playerLoader, waifuLoader,
            jikanFetcher, messageSender));
    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        return new GroupEmbed(group, player, 0);
    }

}
