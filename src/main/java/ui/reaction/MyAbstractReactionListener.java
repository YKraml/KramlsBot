package ui.reaction;

import domain.exceptions.MyOwnException;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.javacord.api.listener.message.reaction.ReactionAddListener;
import ui.embeds.ExceptionEmbed;

abstract public class MyAbstractReactionListener implements ReactionAddListener {


    @Override
    public void onReactionAdd(ReactionAddEvent event) {

        DiscordApi discordApi = event.getApi();
        TextChannel channel;
        User user;
        Emoji emoji;
        Message message;
        Server server;

        channel = event.getChannel();
        emoji = event.getEmoji();
        message = event.requestMessage().join();
        user = event.requestUser().join();

        if (event.getServer().isPresent()) {
            server = event.getServer().get();
        } else {
            event.getChannel().sendMessage("Konnte den Server nicht finden.");
            return;
        }
        if (user.isBot()) {
            return;
        }

        message.removeReactionByEmoji(user, emoji);

        try {
            startRoutine(discordApi, server, channel, message, user, emoji);
        } catch (MyOwnException e) {
            channel.sendMessage(new ExceptionEmbed(e));
        }

    }


    abstract protected void startRoutine(DiscordApi discordApi, Server server,
                                         TextChannel textChannel, Message message, User user, Emoji emoji) throws MyOwnException;


}
