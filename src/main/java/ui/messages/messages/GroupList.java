package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import logic.messages.MessageSender;
import logic.waifu.JikanFetcher;
import domain.PlayerLoader;
import domain.WaifuLoader;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.embeds.group.GroupsListEmbed;
import ui.messages.MyMessageAbs;
import ui.reaction.GroupListListener;

public class GroupList extends MyMessageAbs {

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
    public void startRoutine(Message message) throws MyOwnException {
        this.addCountEmojis(message, player.getGroupList().size());
        message.addReactionAddListener(new GroupListListener(player, playerLoader, waifuLoader,
                jikanFetcher, messageSender));
    }

    @Override
    public EmbedBuilder getContent() throws MyOwnException {
        return new GroupsListEmbed(player, 0);
    }
}
