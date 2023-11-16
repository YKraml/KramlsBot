package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.waifu.dungeon.Dungeon;
import logic.MessageSender;
import logic.waifu.DungeonLoader;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.embeds.dungeon.DungeonsDeletionListEmbed;
import ui.messages.MyMessageAbs;
import ui.reaction.DungeonDeletionListListener;

import java.util.List;

public class DeleteDungeonsMessage extends MyMessageAbs {

    private final DungeonLoader dungeonLoader;
    private final MessageSender messageSender;
    private final String serverId;

    public DeleteDungeonsMessage(DungeonLoader dungeonLoader, MessageSender messageSender,
                                 String serverId) {
        this.dungeonLoader = dungeonLoader;
        this.messageSender = messageSender;
        this.serverId = serverId;
    }

    @Override
    public void startRoutine(Message message) throws MyOwnException {
        List<Dungeon> dungeonList = dungeonLoader.getAllDungeonsFromServer(serverId);
        this.addCountEmojis(message, dungeonList.size());
        message.addReactionAddListener(new DungeonDeletionListListener(dungeonList, dungeonLoader, messageSender,
                serverId));
    }

    @Override
    public EmbedBuilder getContent() throws MyOwnException {
        List<Dungeon> dungeonList = dungeonLoader.getAllDungeonsFromServer(serverId);
        return new DungeonsDeletionListEmbed(dungeonList, 0);
    }
}
