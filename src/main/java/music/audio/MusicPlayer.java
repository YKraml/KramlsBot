package music.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import embeds.music.QueueEmbed;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import music.queue.Queue;
import music.queue.QueueElement;
import music.queue.QueueImpl;
import org.javacord.api.audio.AudioConnection;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;

class MusicPlayer {

  private ServerVoiceChannel serverVoiceChannel;

  private final AudioPlayerManager audioPlayerManager;
  private final AudioPlayer audioPlayer;
  private final MyAudioLoadResultHandler myAudioLoadResultHandler;
  private AudioConnection audioConnection;
  private final Queue queue;
  private final List<Message> messages;

  private MusicPlayer(ServerVoiceChannel serverVoiceChannel, TextChannel textChannel,
      AudioPlayerManager audioPlayerManager, AudioPlayer audioPlayer, Queue queue,
      List<Message> messages) {
    this.serverVoiceChannel = serverVoiceChannel;
    this.audioPlayerManager = audioPlayerManager;
    this.audioPlayer = audioPlayer;

    myAudioLoadResultHandler = new MyAudioLoadResultHandler(this.audioPlayer, textChannel, this);
    this.audioPlayer.addListener(new MyAudioEventAdapter(this));

    this.queue = queue;
    this.messages = messages;
  }

  static MusicPlayer createMusicPlayer(ServerVoiceChannel serverVoiceChannel,
      TextChannel textChannel) {

    AudioPlayerManager audioPlayerManager = new DefaultAudioPlayerManager();
    audioPlayerManager.registerSourceManager(new YoutubeAudioSourceManager());
    Queue queue = new QueueImpl(new ArrayList<>(), new ArrayList<>());
    List<Message> messages = Collections.synchronizedList(new ArrayList<>());

    return new MusicPlayer(serverVoiceChannel, textChannel, audioPlayerManager,
        audioPlayerManager.createPlayer(), queue, messages);
  }

  public void addQueueMessage(Message message) {
    this.messages.add(message);
    int MAX_MESSAGES = 3;
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
    if(audioConnection == null){
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
    this.myAudioLoadResultHandler.setChannel(textChannel);
  }

  private void resetAudioConnection() {
    AudioSource audioSource = new AudioSource(this.serverVoiceChannel.getApi(), audioPlayer);
    if (audioConnection == null || audioConnection.getChannel() != serverVoiceChannel) {
      audioConnection = serverVoiceChannel.connect().join();
      audioConnection.setAudioSource(audioSource);
    }
  }

  private void playCurrentQueueElement() {
    resetAudioConnection();
    queue.getCurrentElement().map(QueueElement::getUrl)
        .ifPresent(url -> audioPlayerManager.loadItem(url, myAudioLoadResultHandler));
  }
}
