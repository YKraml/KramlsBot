package messages.messages;

import exceptions.MyOwnException;
import messages.MyMessage;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class GroupAlreadyExists extends MyMessage {

    private final String groupName;

    public GroupAlreadyExists(String groupName) {
        super();
        this.groupName = groupName;
    }

    @Override
    protected void startRoutine(Message message) throws MyOwnException {

    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        return this.convertStringToEmbed("Gruppe \"" + groupName + "\" existiert schon.");
    }
}
