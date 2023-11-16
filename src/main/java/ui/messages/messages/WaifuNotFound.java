package ui.messages.messages;

import domain.exceptions.MyOwnException;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.messages.MyMessageAbs;

public class WaifuNotFound extends MyMessageAbs {

    private final int waifuNumber;

    public WaifuNotFound(int waifuNumber) {
        this.waifuNumber = waifuNumber;
    }

    @Override
    public void startRoutine(Message message) throws MyOwnException {
        //Ignore.
    }

    @Override
    public EmbedBuilder getContent() throws MyOwnException {
        return this.convertStringToEmbed("Konnte Waifu mit der Nr. " + waifuNumber + " nicht finden.");
    }
}
