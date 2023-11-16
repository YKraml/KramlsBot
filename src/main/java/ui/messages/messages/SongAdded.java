package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.queue.QueueElement;
import logic.music.audio.MusicPlayerManager;
import logic.waifu.PlayerLoader;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.embeds.music.SongAddedEmbed;
import ui.messages.MyMessageAbs;
import ui.reaction.SongAddedEmbedListener;
import ui.Emojis;

public class SongAdded extends MyMessageAbs {

    private final QueueElement queueElement;
    private final MusicPlayerManager musicPlayerManager;
    private final PlayerLoader playerLoader;

    public SongAdded(QueueElement queueElement, MusicPlayerManager musicPlayerManager,
                     PlayerLoader playerLoader) {
        this.queueElement = queueElement;
        this.musicPlayerManager = musicPlayerManager;
        this.playerLoader = playerLoader;
    }

    @Override
    public void startRoutine(Message message) throws MyOwnException {
        message.addReaction(Emojis.INFORMATION_SOURCE.getEmoji());
        message.addReactionAddListener(new SongAddedEmbedListener(musicPlayerManager, playerLoader));
    }

    @Override
    public EmbedBuilder getContent() throws MyOwnException {
        return new SongAddedEmbed(queueElement);
    }

}
