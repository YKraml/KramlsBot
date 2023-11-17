package ui.messages;

import logic.messages.MyMessage;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import domain.Emojis;
import util.Main;

public abstract class MyMessageAbs implements MyMessage {

    protected EmbedBuilder convertStringToEmbed(String toConvert) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle(this.getClass().getSimpleName());
        embedBuilder.setColor(Main.COLOR);
        embedBuilder.setDescription(toConvert);
        return embedBuilder;
    }


    protected void addCountEmojis(Message message, int size) {

        if (size > 10) {
            message.addReaction(Emojis.REWIND.getEmoji());
        }

        for (int i = 0; i < 10; i++) {
            if (i >= size) {
                break;
            }
            message.addReaction(Emojis.getCountEmojis()[i]);
        }

        if (size > 10) {
            message.addReaction(Emojis.FAST_FORWARD.getEmoji());
        }
    }

}
