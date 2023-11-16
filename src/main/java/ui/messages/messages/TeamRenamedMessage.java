package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.messages.MyMessage;

public class TeamRenamedMessage extends MyMessage {


    private final Player player;
    private final String oldName;
    private final String newName;

    public TeamRenamedMessage(Player player, String oldName, String newName) {
        this.player = player;
        this.oldName = oldName;
        this.newName = newName;
    }

    @Override
    protected void startRoutine(Message message) throws MyOwnException {
        //Ignore.
    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        return this.convertStringToEmbed(
                player.getNameTag() + ", das Team \"" + oldName + " heisst jetzt \"" + newName + "\"");
    }
}
