package music.audio;

import exceptions.MyOwnException;
import exceptions.messages.QueueNonExisting;
import java.util.function.Supplier;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

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

  public void playNextSong(ServerVoiceChannel serverVoiceChannel, TextChannel textChannel)
      throws MyOwnException {
    Optional<MusicPlayer> playerOptional = getPlayerByServer(serverVoiceChannel.getServer());

    if (playerOptional.isPresent()) {
      playerOptional.get().setServerVoiceChannel(serverVoiceChannel);
      playerOptional.get().setTextChannel(textChannel);
      playerOptional.get().playNextSong();
    }
  }

  public void playCurrentSong(ServerVoiceChannel voiceChannel, TextChannel textChannel) {
    Server server = voiceChannel.getServer();
    getPlayerByServer(server).ifPresent(musicPlayer -> {
      musicPlayer.setServerVoiceChannel(voiceChannel);
      musicPlayer.setTextChannel(textChannel);
      musicPlayer.playCurrentSong();
    });
  }

  public void playPreviousSong(ServerVoiceChannel voiceChannel, TextChannel textChannel)
      throws MyOwnException {
    Optional<MusicPlayer> playerOptional = getPlayerByServer(voiceChannel.getServer());
    if (playerOptional.isPresent()) {
      playerOptional.get().setServerVoiceChannel(voiceChannel);
      playerOptional.get().setTextChannel(textChannel);
      playerOptional.get().playPreviousSong();
    }
  }

  public void startPlaying(ServerVoiceChannel serverVoiceChannel, TextChannel textChannel)
      throws MyOwnException {
    Optional<MusicPlayer> playerOptional = getPlayerByServer(serverVoiceChannel.getServer());
    if (playerOptional.isPresent()) {
      playerOptional.get().setServerVoiceChannel(serverVoiceChannel);
      playerOptional.get().setTextChannel(textChannel);
      playerOptional.get().startPlaying();
    }
  }

  public void playThisSongNext(ServerVoiceChannel voiceChannel, TextChannel textChannel,
      QueueElement queueElement) throws MyOwnException {
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
    MusicPlayer musicPlayer = new MusicPlayer(serverVoiceChannel, textChannel);
    players.put(server, musicPlayer);
    return musicPlayer;
  }
}
