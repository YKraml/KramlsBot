package ui.messages.messages;

import domain.exceptions.MyOwnException;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.messages.MyMessage;

public class ButtonNotForYou extends MyMessage {

    private final String triedPlayer;
    private final String forPlayer;

    public ButtonNotForYou(String triedPlayer, String forPlayer) {
        this.triedPlayer = triedPlayer;
        this.forPlayer = forPlayer;
    }


    @Override
    protected void startRoutine(Message message) throws MyOwnException {
        //Just ignore.
    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        return this.convertStringToEmbed("%s, der Knopf ist fuer %s".formatted(triedPlayer, forPlayer));
    }
}
