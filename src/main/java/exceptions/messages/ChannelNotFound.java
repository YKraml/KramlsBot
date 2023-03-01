package exceptions.messages;

import exceptions.ExceptionMessage;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.channel.TextChannel;

public class ChannelNotFound implements ExceptionMessage {

    private final Channel channel;

    public ChannelNotFound(TextChannel channel) {
        super();
        this.channel = channel;
    }

    @Override
    public String getContent() {
        return "Konnte channel \"" + channel.getId() + "\" nicht finden";
    }
}
