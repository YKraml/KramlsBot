package logic.music.audio;

import domain.queue.Queue;
import domain.queue.QueueElement;
import domain.exceptions.MyOwnException;
import domain.exceptions.messages.QueueNonExisting;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import javax.inject.Singleton;

import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;

@Singleton
public final class MusicPlayerManager {

  private final Map<Server, MusicPlayer> players;


  public MusicPlayerManager() {
    this.players = Collections.synchronizedMap(new LinkedHashMap<>());
  }

  private Optional<MusicPlayer> getPlayerByServer(Server server) {
    return Optional.ofNullable(players.get(server));
  }

  public void addToQueue(Server server, ServerVoiceChannel voiceChannel, TextChannel textChannel,
      QueueElement queueElement) {
    getPlayerByServer(server).ifPresentOrElse(musicPlayer -> musicPlayer.addSongToQue(queueElement),
        () -> createPlayer(server, voiceChannel, textChannel).addSongToQue(queueElement));
  }

  public void startPlaying(ServerVoiceChannel voiceChannel, TextChannel textChannel) {
    getPlayerByServer(voiceChannel.getServer()).ifPresent(musicPlayer -> {
      musicPlayer.setServerVoiceChannel(voiceChannel);
      musicPlayer.setTextChannel(textChannel);
      musicPlayer.start();
    });
  }

  public void playNextSong(ServerVoiceChannel serverVoiceChannel, TextChannel textChannel) {
    getPlayerByServer(serverVoiceChannel.getServer()).ifPresent(musicPlayer -> {
      musicPlayer.setServerVoiceChannel(serverVoiceChannel);
      musicPlayer.setTextChannel(textChannel);
      musicPlayer.playNextSong();
    });
  }

  public void restartSong(ServerVoiceChannel voiceChannel, TextChannel textChannel) {
    Server server = voiceChannel.getServer();
    getPlayerByServer(server).ifPresent(musicPlayer -> {
      musicPlayer.setServerVoiceChannel(voiceChannel);
      musicPlayer.setTextChannel(textChannel);
      musicPlayer.restartSong();
    });
  }

  public void playPreviousSong(ServerVoiceChannel voiceChannel, TextChannel textChannel) {
    getPlayerByServer(voiceChannel.getServer()).ifPresent(musicPlayer -> {
      musicPlayer.setServerVoiceChannel(voiceChannel);
      musicPlayer.setTextChannel(textChannel);
      musicPlayer.playPreviousSong();
    });
  }

  public void playThisSongNext(ServerVoiceChannel voiceChannel, TextChannel textChannel,
      QueueElement queueElement) {

    MusicPlayer musicPlayer = getPlayerByServer(voiceChannel.getServer())
        .orElseGet(() -> createPlayer(voiceChannel.getServer(), voiceChannel, textChannel));
    musicPlayer.setServerVoiceChannel(voiceChannel);
    musicPlayer.setTextChannel(textChannel);
    musicPlayer.playNow(queueElement);
  }

  public Queue getQueueByServer(Server server) throws MyOwnException {
    return getPlayerByServer(server)
        .orElseThrow(() -> new MyOwnException(new QueueNonExisting(server), null))
        .getQueue();
  }

  public void stopPlaying(Server server) {
    getPlayerByServer(server).ifPresent(MusicPlayer::stop);
    players.remove(server);
  }

  public void addQueueMessage(Message message) {
    message.getServer().flatMap(this::getPlayerByServer)
        .ifPresent(player -> player.addQueueMessage(message));
  }

  private MusicPlayer createPlayer(Server server, ServerVoiceChannel serverVoiceChannel,
      TextChannel textChannel) {
    MusicPlayer musicPlayer = MusicPlayer.createMusicPlayer(serverVoiceChannel, textChannel);
    players.put(server, musicPlayer);
    return musicPlayer;
  }
}
