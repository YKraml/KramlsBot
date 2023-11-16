package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.messages.MyMessageAbs;

public class GuessedWrong extends MyMessageAbs {

    private final Player player;

    public GuessedWrong(Player player) {
        this.player = player;
    }


    @Override
    public void startRoutine(Message message) throws MyOwnException {
        //Ignore.
    }

    @Override
    public EmbedBuilder getContent() throws MyOwnException {
        return convertStringToEmbed(player.getNameTag() + ", du liegst falsch.");
    }
}
