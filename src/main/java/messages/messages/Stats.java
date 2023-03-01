package messages.messages;

import embeds.StatEmbed;
import exceptions.MyOwnException;
import messages.MyMessage;
import waifu.model.Player;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class Stats extends MyMessage {


    private final Player player;
    private final User user;
    private final Server server;

    public Stats(Player player, User user, Server server) {
        this.player = player;
        this.user = user;
        this.server = server;
    }


    @Override
    protected void startRoutine(Message message) throws MyOwnException {

    }

    @Override
    protected EmbedBuilder getContent() {
        return new StatEmbed(player, user, server);
    }
}
