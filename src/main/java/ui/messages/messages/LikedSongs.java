package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import logic.MessageSender;
import logic.music.audio.MusicPlayerManager;
import logic.waifu.PlayerLoader;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import ui.embeds.music.LikedSongsEmbed;
import ui.messages.MyMessageAbs;
import ui.reaction.SongsListReactionListener;

public class LikedSongs extends MyMessageAbs {
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
    public void startRoutine(Message message) throws MyOwnException {
        this.addCountEmojis(message, player.getLikedSongs().size());
        message.addReactionAddListener(new SongsListReactionListener(player, server, user,
                musicPlayerManager, playerLoader, messageSender));
    }

    @Override
    public EmbedBuilder getContent() throws MyOwnException {
        return new LikedSongsEmbed(player, 0);
    }

}
