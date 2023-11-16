package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.waifu.dungeon.Dungeon;
import domain.waifu.dungeon.Team;
import logic.waifu.PlayerLoader;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.embeds.dungeon.DungeonListEmbed;
import ui.messages.MessageSender;
import ui.messages.MyMessage;
import ui.reaction.DungeonListListener;

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
