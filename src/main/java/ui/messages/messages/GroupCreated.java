package ui.messages.messages;

import domain.exceptions.MyOwnException;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.messages.MyMessage;

public class GroupCreated extends MyMessage {

    private final String groupName;

    public GroupCreated(String groupName) {
        this.groupName = groupName;
    }

    @Override
    protected void startRoutine(Message message) throws MyOwnException {
        //Ignore.
    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        return this.convertStringToEmbed("Gruppe '%s' erstellt.".formatted(groupName));
    }
}
