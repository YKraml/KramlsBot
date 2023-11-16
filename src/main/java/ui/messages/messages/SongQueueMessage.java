package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.queue.Queue;
import logic.music.audio.MusicPlayerManager;
import logic.waifu.PlayerLoader;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.embeds.music.QueueEmbed;
import ui.messages.MyMessageAbs;
import ui.reaction.QueueListener;
import util.Emojis;

public class SongQueueMessage extends MyMessageAbs {

    private final Queue queue;
    private final MusicPlayerManager musicPlayerManager;
    private final PlayerLoader playerLoader;

    public SongQueueMessage(Queue queueByServer, MusicPlayerManager musicPlayerManager, PlayerLoader playerLoader) {
        this.queue = queueByServer;
        this.musicPlayerManager = musicPlayerManager;
        this.playerLoader = playerLoader;
    }

    @Override
    public void startRoutine(Message message) throws MyOwnException {
        message.addReaction(Emojis.REWIND.getEmoji());
        message.addReaction(Emojis.ARROWS_COUNTERCLOCKWISE.getEmoji());
        message.addReaction(Emojis.FAST_FORWARD.getEmoji());
        message.addReaction(Emojis.STAR.getEmoji());
        message.addReactionAddListener(new QueueListener(queue, playerLoader, musicPlayerManager));
        musicPlayerManager.addQueueMessage(message);
    }

    @Override
    public EmbedBuilder getContent() throws MyOwnException {
        return new QueueEmbed(queue);
    }


}
