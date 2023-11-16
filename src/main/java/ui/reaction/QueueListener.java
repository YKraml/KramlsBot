package ui.reaction;

import domain.exceptions.MyOwnException;
import domain.queue.Queue;
import domain.queue.QueueElement;
import domain.waifu.Player;
import logic.music.audio.MusicPlayerManager;
import logic.waifu.PlayerLoader;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import ui.embeds.music.QueueEmbed;
import logic.ChannelFinder;
import ui.Emojis;

import java.util.Optional;

public class QueueListener extends MyAbstractReactionListener {

    private final Queue queue;
    private final PlayerLoader playerLoader;
    private final MusicPlayerManager musicPlayerManager;

    public QueueListener(Queue queue, PlayerLoader playerLoader,
                         MusicPlayerManager musicPlayerManager) {
        this.queue = queue;
        this.playerLoader = playerLoader;
        this.musicPlayerManager = musicPlayerManager;
    }

    @Override
    protected void startRoutine(DiscordApi discordApi, Server server, TextChannel textChannel,
                                Message message, User user, Emoji emoji) throws MyOwnException {

        ServerVoiceChannel serverVoiceChannel = new ChannelFinder(
                discordApi).getServerVoiceChannelByMember(server, user);

        if (emoji.equalsEmoji(Emojis.FAST_FORWARD.getEmoji())) {
            musicPlayerManager.playNextSong(serverVoiceChannel, textChannel);
        }

        if (emoji.equalsEmoji(Emojis.REWIND.getEmoji())) {
            musicPlayerManager.playPreviousSong(serverVoiceChannel, textChannel);
        }

        if (emoji.equalsEmoji(Emojis.ARROWS_COUNTERCLOCKWISE.getEmoji())) {
            musicPlayerManager.restartSong(serverVoiceChannel, textChannel);

        }

        if (emoji.equalsEmoji(Emojis.STAR.getEmoji())) {

            Optional<QueueElement> currentElement = queue.getCurrentElement();
            if (currentElement.isEmpty()) {
                return;
            }

            Player playerThatLiked = playerLoader.getPlayerByUser(user);
            playerThatLiked.addQueueElement(currentElement.get());
            playerLoader.savePlayer(playerThatLiked);
            textChannel.sendMessage(
                    "'%s' deinen Favoriten hinzugefügt. Nutze 'songs' um deine Favoriten zu sehen.".formatted(
                            currentElement.get().getName()));
        }

        message.edit(new QueueEmbed(queue));

    }
}
