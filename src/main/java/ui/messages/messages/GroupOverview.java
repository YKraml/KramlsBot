package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.waifu.Group;
import domain.waifu.Player;
import logic.waifu.JikanFetcher;
import logic.waifu.PlayerLoader;
import logic.waifu.WaifuLoader;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.embeds.group.GroupEmbed;
import logic.MessageSender;
import ui.messages.MyMessage;
import ui.reaction.GroupListener;

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
        this.addCountEmojis(message, group.getWaifuList().size());
        message.addReactionAddListener(new GroupListener(group, player, playerLoader, waifuLoader,
                jikanFetcher, messageSender));
    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        return new GroupEmbed(group, player, 0);
    }

}
