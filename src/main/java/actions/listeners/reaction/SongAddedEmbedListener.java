package actions.listeners.reaction;

import actions.listeners.reaction.util.MyAbstractReactionListener;
import exceptions.MyOwnException;
import music.audio.MusicPlayerManager;
import messages.MessageSenderImpl;
import messages.messages.SongQueueMessage;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import waifu.loader.PlayerLoader;

public class SongAddedEmbedListener extends MyAbstractReactionListener {

    private final MusicPlayerManager musicPlayerManager;
    private final PlayerLoader playerLoader;

    public SongAddedEmbedListener(MusicPlayerManager musicPlayerManager, PlayerLoader playerLoader) {
        this.musicPlayerManager = musicPlayerManager;
        this.playerLoader = playerLoader;
    }

    @Override
    protected void startRoutine(DiscordApi discordApi, Server server, TextChannel textChannel,
        Message message, User user, Emoji emoji) throws MyOwnException {
        MessageSenderImpl result;
        synchronized (MessageSenderImpl.class) {
            result = new MessageSenderImpl();
        }
        result.send(new SongQueueMessage(musicPlayerManager.getQueueByServer(server),
            musicPlayerManager, playerLoader), textChannel);
    }
}
