package ui.reaction;

import domain.exceptions.MyOwnException;
import model.jikan.anime.animeByIdFull.AnimeFullById;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.listener.message.reaction.ReactionAddListener;
import ui.messages.MessageSenderImpl;
import ui.messages.messages.AnimeSynopsis;
import util.Emojis;

public class SynopsisReactionListener extends MyAbstractReactionListener implements
        ReactionAddListener {


    private final AnimeFullById anime;

    public SynopsisReactionListener(AnimeFullById anime) {
        this.anime = anime;
    }


    @Override
    public void startRoutine(DiscordApi discordApi, Server server, TextChannel textChannel,
                             Message message, User user, Emoji emoji) throws MyOwnException {
        if (emoji.equalsEmoji(Emojis.INFORMATION_SOURCE.getEmoji())) {
            MessageSenderImpl result;
            synchronized (MessageSenderImpl.class) {
                result = new MessageSenderImpl();
            }
            result.send(new AnimeSynopsis(anime), textChannel);
        }
    }
}
