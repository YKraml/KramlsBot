package messages.messages;

import actions.listeners.reaction.DungeonListListener;
import embeds.dungeon.DungeonListEmbed;
import exceptions.MyOwnException;
import messages.MessageSender;
import messages.MyMessage;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import waifu.loader.PlayerLoader;
import waifu.model.dungeon.Dungeon;
import waifu.model.dungeon.Team;

import java.util.List;

public class DungeonList extends MyMessage {
    private final Team team;
    private final List<Dungeon> dungeonList;
    private final PlayerLoader playerLoader;
    private final MessageSender messageSender;

    public DungeonList(Team team, List<Dungeon> dungeonList, PlayerLoader playerLoader,
        MessageSender messageSender) {
        this.team = team;
        this.dungeonList = dungeonList;
        this.playerLoader = playerLoader;
        this.messageSender = messageSender;
    }

    @Override
    protected void startRoutine(Message message) throws MyOwnException {
        this.addCountEmojis(message, dungeonList.size());
        message.addReactionAddListener(new DungeonListListener(dungeonList, team, playerLoader,
            messageSender));
    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        return new DungeonListEmbed(dungeonList, 0);
    }

}
