package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.messages.MyMessage;

public class GuessedWrong extends MyMessage {

    private final Player player;

    public GuessedWrong(Player player) {
        this.player = player;
    }


    @Override
    protected void startRoutine(Message message) throws MyOwnException {
        //Ignore.
    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        return convertStringToEmbed(player.getNameTag() + ", du liegst falsch.");
    }
}
