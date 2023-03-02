package messages.messages;

import actions.listeners.reaction.TeamListListener;
import embeds.dungeon.TeamsListEmbed;
import exceptions.MyOwnException;
import messages.MessageSender;
import messages.MyMessage;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import waifu.loader.DungeonLoader;
import waifu.loader.PlayerLoader;
import waifu.model.Player;

public class TeamsOverview extends MyMessage {
    private final Player player;
    private final DungeonLoader dungeonLoader;
    private final PlayerLoader playerLoader;
    private final MessageSender messageSender;

    public TeamsOverview(Player player, DungeonLoader dungeonLoader, PlayerLoader playerLoader,
        MessageSender messageSender) {
        super();
        this.player = player;
        this.dungeonLoader = dungeonLoader;
        this.playerLoader = playerLoader;
        this.messageSender = messageSender;
    }

    @Override
    protected void startRoutine(Message message) throws MyOwnException {
        this.addCountEmojis(message, player.getTeamList().size());
        message.addReactionAddListener(new TeamListListener(player, dungeonLoader, playerLoader,
            messageSender));
    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        return new TeamsListEmbed(player, 0);
    }
}
