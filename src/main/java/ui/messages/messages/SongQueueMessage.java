package ui.messages.messages;

import ui.reaction.QueueListener;
import ui.embeds.music.QueueEmbed;
import domain.exceptions.MyOwnException;
import logic.music.audio.MusicPlayerManager;
import domain.queue.Queue;
import ui.messages.MyMessage;
import util.Emojis;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import logic.waifu.PlayerLoader;

public class SongQueueMessage extends MyMessage {

    private final Queue queue;
    private final MusicPlayerManager musicPlayerManager;
    private final PlayerLoader playerLoader;

    public SongQueueMessage(Queue queueByServer, MusicPlayerManager musicPlayerManager, PlayerLoader playerLoader) {
        this.queue = queueByServer;
        this.musicPlayerManager = musicPlayerManager;
        this.playerLoader = playerLoader;
    }

    @Override
    protected void startRoutine(Message message) throws MyOwnException {
        message.addReaction(Emojis.REWIND.getEmoji());
        message.addReaction(Emojis.ARROWS_COUNTERCLOCKWISE.getEmoji());
        message.addReaction(Emojis.FAST_FORWARD.getEmoji());
        message.addReaction(Emojis.STAR.getEmoji());
        message.addReactionAddListener(new QueueListener(queue, playerLoader, musicPlayerManager));
        musicPlayerManager.addQueueMessage(message);
    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        return new QueueEmbed(queue);
    }


}
