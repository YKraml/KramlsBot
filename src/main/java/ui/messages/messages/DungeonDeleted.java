package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.waifu.dungeon.Dungeon;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.messages.MyMessageAbs;

public class DungeonDeleted extends MyMessageAbs {

    private final Dungeon dungeon;

    public DungeonDeleted(Dungeon dungeonToDelete) {
        dungeon = dungeonToDelete;
    }

    @Override
    public void startRoutine(Message message) throws MyOwnException {
        //Ignore.
    }

    @Override
    public EmbedBuilder getContent() throws MyOwnException {
        return this.convertStringToEmbed("Dungeon '%s' wurde gelöscht.".formatted(dungeon.getName()));
    }
}
