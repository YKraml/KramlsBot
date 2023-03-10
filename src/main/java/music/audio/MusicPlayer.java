package music.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import embeds.music.QueueEmbed;
import java.util.ArrayList;
import java.util.List;
import music.queue.Queue;
import music.queue.QueueElement;
import music.queue.QueueImpl;
import org.javacord.api.audio.AudioConnection;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;

class MusicPlayer {

  private final static int MAX_MESSAGES = 3;
  private ServerVoiceChannel serverVoiceChannel;
  private final AudioPlayerManager audioPlayerManager;
  private final AudioPlayer audioPlayer;
  private final MyAudioLoadResultListener audioLoadResultHandler;
  private final Queue queue;
  private final List<Message> messages;
  private AudioConnection audioConnection;

  private MusicPlayer(AudioPlayerManager audioPlayerManager, AudioPlayer audioPlayer,
      MyAudioLoadResultListener audioLoadResultHandler, ServerVoiceChannel serverVoiceChannel) {
    this.serverVoiceChannel = serverVoiceChannel;
    this.audioPlayerManager = audioPlayerManager;
    this.audioPlayer = audioPlayer;
    this.audioLoadResultHandler = audioLoadResultHandler;

    this.queue = new QueueImpl(new ArrayList<>(), new ArrayList<>());
    this.messages = new ArrayList<>();
  }

  static MusicPlayer createMusicPlayer(ServerVoiceChannel serverVoiceChannel,
      TextChannel textChannel) {

    AudioPlayerManager audioPlayerManager = new DefaultAudioPlayerManager();
    AudioPlayer audioPlayer = audioPlayerManager.createPlayer();
    MyAudioLoadResultListener audioLoadResultHandler = new MyAudioLoadResultListener(audioPlayer,
        textChannel);

    MusicPlayer musicPlayer = new MusicPlayer(audioPlayerManager, audioPlayer,
        audioLoadResultHandler, serverVoiceChannel);

    audioPlayerManager.registerSourceManager(new YoutubeAudioSourceManager());
    audioPlayer.addListener(new MyAudioEventListener(musicPlayer));
    audioLoadResultHandler.setMusicPlayer(musicPlayer);

    return musicPlayer;
  }

  public void addQueueMessage(Message message) {
    this.messages.add(message);
    if (messages.size() > MAX_MESSAGES) {
      this.messages.remove(MAX_MESSAGES);
    }
  }

  public void updateMessages() {
    new Thread(() -> messages.forEach(message -> message.edit(new QueueEmbed(queue)))).start();
  }

  Queue getQueue() {
    return queue;
  }

  void addSongToQue(QueueElement queueElement) {
    queue.addToEnd(queueElement);
    updateMessages();
  }

  public void start() {
    if (audioConnection == null) {
      playNextSong();
    }
  }

  void playNow(QueueElement queueElement) {
    queue.addToFront(queueElement);
    playNextSong();
  }

  void playNextSong() {
    queue.goToNextElement();
    playCurrentQueueElement();
  }

  void playPreviousSong() {
    queue.goToPreviousElement();
    playCurrentQueueElement();
  }

  void restartSong() {
    playCurrentQueueElement();
  }

  void stop() {
    audioPlayer.stopTrack();
    if (audioConnection != null) {
      audioConnection.close().join();
      audioConnection = null;
    }
  }

  void setServerVoiceChannel(ServerVoiceChannel serverVoiceChannel) {
    this.serverVoiceChannel = serverVoiceChannel;
  }

  void setTextChannel(TextChannel textChannel) {
    this.audioLoadResultHandler.setChannel(textChannel);
  }

  private void resetAudioConnection() {
    if (audioConnection == null || audioConnection.getChannel() != serverVoiceChannel) {
      audioConnection = serverVoiceChannel.connect().join();
      audioConnection.setAudioSource(new AudioSource(serverVoiceChannel.getApi(), audioPlayer));
    }
  }

  private void playCurrentQueueElement() {
    resetAudioConnection();
    queue.getCurrentElement().map(QueueElement::getUrl)
        .ifPresent(url -> audioPlayerManager.loadItem(url, audioLoadResultHandler));
  }
}
