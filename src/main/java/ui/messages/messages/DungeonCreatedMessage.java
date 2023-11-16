package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.waifu.dungeon.Dungeon;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.embeds.dungeon.DungeonCreatedEmbed;
import ui.messages.MyMessageAbs;

public class DungeonCreatedMessage extends MyMessageAbs {

    private final Dungeon dungeon;

    public DungeonCreatedMessage(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    @Override
    public void startRoutine(Message message) throws MyOwnException {
        //Ignore.
    }

    @Override
    public EmbedBuilder getContent() throws MyOwnException {
        return new DungeonCreatedEmbed(dungeon);
    }
}
