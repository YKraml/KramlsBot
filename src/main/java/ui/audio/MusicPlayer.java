package ui.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import domain.queue.Queue;
import domain.queue.QueueElement;
import domain.queue.QueueImpl;
import logic.messages.MessageSender;
import logic.messages.Observable;
import org.javacord.api.audio.AudioConnection;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.channel.TextChannel;

import java.util.ArrayList;
import java.util.function.Consumer;

class MusicPlayer extends Observable {

    private final AudioPlayerManager audioPlayerManager;
    private final AudioPlayer audioPlayer;
    private final MyAudioLoadResultListener audioLoadResultHandler;
    private final Queue queue;
    private ServerVoiceChannel serverVoiceChannel;
    private AudioConnection audioConnection;

    private MusicPlayer(AudioPlayerManager audioPlayerManager, AudioPlayer audioPlayer, MyAudioLoadResultListener audioLoadResultHandler, ServerVoiceChannel serverVoiceChannel) {
        this.serverVoiceChannel = serverVoiceChannel;
        this.audioPlayerManager = audioPlayerManager;
        this.audioPlayer = audioPlayer;
        this.audioLoadResultHandler = audioLoadResultHandler;
        this.queue = new QueueImpl(new ArrayList<>(), new ArrayList<>());
    }

    static MusicPlayer createMusicPlayer(ServerVoiceChannel serverVoiceChannel, TextChannel textChannel, MessageSender messageSender) {

        AudioPlayerManager audioPlayerManager = new DefaultAudioPlayerManager();
        AudioPlayer audioPlayer = audioPlayerManager.createPlayer();
        MyAudioLoadResultListener audioLoadResultHandler = new MyAudioLoadResultListener(audioPlayer, textChannel, messageSender);

        MusicPlayer musicPlayer = new MusicPlayer(audioPlayerManager, audioPlayer, audioLoadResultHandler, serverVoiceChannel);

        audioPlayerManager.registerSourceManager(new YoutubeAudioSourceManager());
        audioPlayer.addListener(new MyAudioEventListener(musicPlayer));
        audioLoadResultHandler.setMusicPlayer(musicPlayer);

        return musicPlayer;
    }


    Queue getQueue() {
        return queue;
    }

    void addSongToQue(QueueElement queueElement) {
        queue.addToEnd(queueElement);
        messageObservers();
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
        Consumer<String> stringConsumer = url -> audioPlayerManager.loadItem(url, audioLoadResultHandler);
        queue.getCurrentElement().map(QueueElement::getUrl).ifPresent(stringConsumer);
    }
}
