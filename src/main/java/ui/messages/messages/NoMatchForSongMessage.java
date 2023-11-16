package ui.messages.messages;

import domain.exceptions.MyOwnException;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.messages.MyMessageAbs;

public class NoMatchForSongMessage extends MyMessageAbs {
    @Override
    public void startRoutine(Message message) throws MyOwnException {

    }

    @Override
    public EmbedBuilder getContent() throws MyOwnException {
        return convertStringToEmbed("Kein Treffer für die Url.");
    }
}
