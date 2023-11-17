package ui.audio;

import domain.exceptions.MyOwnException;
import domain.exceptions.messages.QueueNonExisting;
import domain.queue.Queue;
import domain.queue.QueueElement;
import logic.MusicPlayerManager;
import logic.messages.MessageSender;
import logic.messages.Observer;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Singleton
public final class MusicPlayerManagerImpl implements MusicPlayerManager {

    private final Map<Server, MusicPlayer> players;
    private final MessageSender messageSender;


    @Inject
    public MusicPlayerManagerImpl(MessageSender messageSender) {
        this.messageSender = messageSender;
        this.players = Collections.synchronizedMap(new LinkedHashMap<>());
    }

    private Optional<MusicPlayer> getPlayerByServer(Server server) {
        return Optional.ofNullable(players.get(server));
    }

    @Override
    public void addToQueue(Server server, ServerVoiceChannel voiceChannel, TextChannel textChannel, QueueElement queueElement) {
        getPlayerByServer(server).ifPresentOrElse(musicPlayer -> musicPlayer.addSongToQue(queueElement), () -> createPlayer(server, voiceChannel, textChannel).addSongToQue(queueElement));
    }

    @Override
    public void startPlaying(ServerVoiceChannel voiceChannel, TextChannel textChannel) {
        getPlayerByServer(voiceChannel.getServer()).ifPresent(musicPlayer -> {
            musicPlayer.setServerVoiceChannel(voiceChannel);
            musicPlayer.setTextChannel(textChannel);
            musicPlayer.start();
        });
    }

    @Override
    public void playNextSong(ServerVoiceChannel serverVoiceChannel, TextChannel textChannel) {
        getPlayerByServer(serverVoiceChannel.getServer()).ifPresent(musicPlayer -> {
            musicPlayer.setServerVoiceChannel(serverVoiceChannel);
            musicPlayer.setTextChannel(textChannel);
            musicPlayer.playNextSong();
        });
    }

    @Override
    public void restartSong(ServerVoiceChannel voiceChannel, TextChannel textChannel) {
        Server server = voiceChannel.getServer();
        getPlayerByServer(server).ifPresent(musicPlayer -> {
            musicPlayer.setServerVoiceChannel(voiceChannel);
            musicPlayer.setTextChannel(textChannel);
            musicPlayer.restartSong();
        });
    }

    @Override
    public void playPreviousSong(ServerVoiceChannel voiceChannel, TextChannel textChannel) {
        getPlayerByServer(voiceChannel.getServer()).ifPresent(musicPlayer -> {
            musicPlayer.setServerVoiceChannel(voiceChannel);
            musicPlayer.setTextChannel(textChannel);
            musicPlayer.playPreviousSong();
        });
    }

    @Override
    public void playThisSongNext(ServerVoiceChannel voiceChannel, TextChannel textChannel, QueueElement queueElement) {
        MusicPlayer musicPlayer = getPlayerByServer(voiceChannel.getServer()).orElseGet(() -> createPlayer(voiceChannel.getServer(), voiceChannel, textChannel));
        musicPlayer.setServerVoiceChannel(voiceChannel);
        musicPlayer.setTextChannel(textChannel);
        musicPlayer.playNow(queueElement);
    }

    @Override
    public Queue getQueueByServer(Server server) throws MyOwnException {
        return getPlayerByServer(server).orElseThrow(() -> new MyOwnException(new QueueNonExisting(server), null)).getQueue();
    }

    @Override
    public void stopPlaying(Server server) {
        getPlayerByServer(server).ifPresent(MusicPlayer::stop);
        players.remove(server);
    }

    @Override
    public void addQueueMessage(Message message, Observer observer) {
        message.getServer().flatMap(this::getPlayerByServer).ifPresent(player -> player.addObserver(observer));
    }

    private MusicPlayer createPlayer(Server server, ServerVoiceChannel serverVoiceChannel, TextChannel textChannel) {
        MusicPlayer musicPlayer = MusicPlayer.createMusicPlayer(serverVoiceChannel, textChannel, messageSender);
        players.put(server, musicPlayer);
        return musicPlayer;
    }
}
