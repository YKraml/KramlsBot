package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import domain.PlayerLoader;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import ui.embeds.StatEmbed;
import ui.messages.MyMessageAbs;

public class Stats extends MyMessageAbs {


    private final User user;
    private final Server server;
    private final PlayerLoader playerLoader;

    public Stats(User user, Server server, PlayerLoader playerLoader) {
        this.user = user;
        this.server = server;
        this.playerLoader = playerLoader;
    }


    @Override
    public void startRoutine(Message message) throws MyOwnException {
        //Ignore.
    }

    @Override
    public EmbedBuilder getContent() throws MyOwnException {
        Player player = playerLoader.getPlayerByUser(user);
        return new StatEmbed(player, user, server);
    }
}
