package ui.messages.messages;

import domain.exceptions.MyOwnException;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.embeds.ExceptionEmbed;
import ui.messages.MyMessage;

public class ExceptionHappenedMessage extends MyMessage {

    private final MyOwnException myOwnException;

    public ExceptionHappenedMessage(MyOwnException myOwnException) {
        this.myOwnException = myOwnException;
    }

    @Override
    protected void startRoutine(Message message) throws MyOwnException {
        //Ignore.
    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        return new ExceptionEmbed(myOwnException);
    }
}
