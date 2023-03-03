package messages.messages;

import actions.listeners.reaction.SongAddedEmbedListener;
import discord.Emojis;
import embeds.music.SongAddedEmbed;
import exceptions.MyOwnException;
import messages.MyMessage;
import music.audio.MusicPlayerManager;
import music.queue.QueueElement;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import waifu.loader.PlayerLoader;

public class SongAdded extends MyMessage {

    private final QueueElement queueElement;
    private final MusicPlayerManager musicPlayerManager;
    private final PlayerLoader playerLoader;

    public SongAdded(QueueElement queueElement, MusicPlayerManager musicPlayerManager,
        PlayerLoader playerLoader) {
        super();
        this.queueElement = queueElement;
        this.musicPlayerManager = musicPlayerManager;
        this.playerLoader = playerLoader;
    }

    @Override
    protected void startRoutine(Message message) throws MyOwnException {
        message.addReaction(Emojis.INFORMATION_SOURCE.getEmoji());
        message.addReactionAddListener(new SongAddedEmbedListener(musicPlayerManager, playerLoader));
    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        return new SongAddedEmbed(queueElement);
    }

}
