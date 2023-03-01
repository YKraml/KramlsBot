package music.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import embeds.music.QueueEmbed;
import exceptions.MyOwnException;
import exceptions.messages.LastSongNonExisting;
import exceptions.messages.NextSongNonExisting;
import org.javacord.api.audio.AudioConnection;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class MusicPlayer {

    private ServerVoiceChannel serverVoiceChannel;

    private final AudioPlayerManager audioPlayerManager;
    private final AudioPlayer audioPlayer;
    private final MyAudioLoadResultHandler myAudioLoadResultHandler;
    private AudioConnection audioConnection;

    private final Queue queue;
    private final List<Message> messages;

    MusicPlayer(ServerVoiceChannel serverVoiceChannel, TextChannel textChannel) {
        this.serverVoiceChannel = serverVoiceChannel;

        this.audioPlayerManager = new DefaultAudioPlayerManager();
        audioPlayerManager.registerSourceManager(new YoutubeAudioSourceManager());
        audioPlayer = audioPlayerManager.createPlayer();
        myAudioLoadResultHandler = new MyAudioLoadResultHandler(audioPlayer, textChannel, this);

        audioPlayer.addListener(new MyAudioEventAdapter(this));

        this.queue = new QueueImpl(new ArrayList<>(), new ArrayList<>());
        this.messages = Collections.synchronizedList(new ArrayList<>());
    }

    void addSongToQue(QueueElement queueElement) {
        queue.addToEnd(queueElement);
        this.updateMessages();
    }

    void playNow(QueueElement queueElement) throws MyOwnException {
        this.queue.addToStart(queueElement);
        this.playNextSong();
    }

    void playNextSong() throws MyOwnException {
        queue.goToNextElement();
        if (queue.getCurrentElement() == null) {
            throw new MyOwnException(new NextSongNonExisting(), null);
        }
        resetAudioConnection();
        audioPlayerManager.loadItem(queue.getCurrentElement().getUrl(), myAudioLoadResultHandler);

    }

    void playPreviousSong() throws MyOwnException {
        if (!queue.hasPreviousSongs()) {
            throw new MyOwnException(new LastSongNonExisting(), null);
        }
        resetAudioConnection();
        queue.goToPreviousElement();
        audioPlayerManager.loadItem(queue.getCurrentElement().getUrl(), myAudioLoadResultHandler);
    }

    void playCurrentSong() {
        resetAudioConnection();
        audioPlayerManager.loadItem(queue.getCurrentElement().getUrl(), myAudioLoadResultHandler);
    }

    void startPlaying() throws MyOwnException {
        if (queue.getCurrentElement() == null) {
            this.playNextSong();
        }
        this.audioConnection.moveTo(this.serverVoiceChannel);
    }

    void stop() {
        this.audioPlayer.stopTrack();
        if (this.audioConnection != null) {
            this.audioConnection.close().join();
            this.audioConnection = null;
        }
    }

    private void resetAudioConnection() {
        AudioSource audioSource = new AudioSource(this.serverVoiceChannel.getApi(), audioPlayer);
        if (audioConnection == null) {
            audioConnection = this.serverVoiceChannel.connect().join();
            audioConnection.setAudioSource(audioSource);
        } else if (audioConnection.getChannel() != this.serverVoiceChannel) {
            audioConnection = this.serverVoiceChannel.connect().join();
            audioConnection.setAudioSource(audioSource);
        }
    }

    Queue getQueue() {
        return queue;
    }


    void setServerVoiceChannel(ServerVoiceChannel serverVoiceChannel) {
        this.serverVoiceChannel = serverVoiceChannel;
    }

    void setTextChannel(TextChannel textChannel) {
        this.myAudioLoadResultHandler.setChannel(textChannel);
    }

    public void addQueueMessage(Message message) {
        this.messages.add(message);
        if (messages.size() > 10) {
            this.messages.remove(10);
        }
    }

    public void updateMessages() {
        new Thread(() -> messages.forEach(message -> message.edit(new QueueEmbed(queue)))).start();
    }
}
