package ui.messages.messages;

import domain.exceptions.MyOwnException;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.messages.MyMessage;

public class DungeonNotFound extends MyMessage {

    private final int listPosition;

    public DungeonNotFound(int listPosition) {
        this.listPosition = listPosition;
    }

    @Override
    protected void startRoutine(Message message) throws MyOwnException {
        //Ignore.
    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        return this.convertStringToEmbed(
                "Konnte keinen Dungeon an der Stelle '%d' finden".formatted(listPosition));
    }
}
