package ui.messages.messages;

import domain.exceptions.MyOwnException;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.messages.MyMessageAbs;

public class GroupCreated extends MyMessageAbs {

    private final String groupName;

    public GroupCreated(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public void startRoutine(Message message) throws MyOwnException {
        //Ignore.
    }

    @Override
    public EmbedBuilder getContent() throws MyOwnException {
        return this.convertStringToEmbed("Gruppe '%s' erstellt.".formatted(groupName));
    }
}
