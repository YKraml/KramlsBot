package messages.messages;

import actions.listeners.reaction.lists.SongsListReactionListener;
import embeds.music.LikedSongsEmbed;
import exceptions.MyOwnException;
import messages.MessageSender;
import messages.MyMessage;
import music.audio.MusicPlayerManager;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import waifu.loader.PlayerLoader;
import waifu.model.Player;

public class LikedSongs extends MyMessage {
    private final Player player;
    private final Server server;
    private final User user;
    private final PlayerLoader playerLoader;
    private final MessageSender messageSender;
    private final MusicPlayerManager musicPlayerManager;

    public LikedSongs(Player player, Server server, User user, PlayerLoader playerLoader,
        MessageSender messageSender, MusicPlayerManager musicPlayerManager) {
        this.player = player;
        this.server = server;
        this.user = user;
        this.playerLoader = playerLoader;
        this.messageSender = messageSender;
        this.musicPlayerManager = musicPlayerManager;
    }


    @Override
    protected void startRoutine(Message message) throws MyOwnException {
        this.addCountEmojis(message, player.getLikedSongs().size());
        message.addReactionAddListener(new SongsListReactionListener(player, server, user,
            musicPlayerManager, playerLoader, messageSender));
    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        return new LikedSongsEmbed(player, 0);
    }

}
