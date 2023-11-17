package ui.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import logic.messages.MessageSender;
import org.javacord.api.entity.channel.TextChannel;

class MyAudioLoadResultListener implements AudioLoadResultHandler {

    private final AudioPlayer player;
    private TextChannel channel;
    private MusicPlayer musicPlayer;
    private final MessageSender messageSender;

    public MyAudioLoadResultListener(AudioPlayer player, TextChannel channel, MessageSender messageSender) {
        this.player = player;
        this.channel = channel;
        this.messageSender = messageSender;
    }

    @Override
    public void trackLoaded(AudioTrack track) {
        musicPlayer.messageObservers();
        player.playTrack(track);
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist) {
        for (AudioTrack track : playlist.getTracks()) {
            player.playTrack(track);
        }
    }

    @Override
    public void noMatches() {
        messageSender.sendSafeNoMatchForSongMessage(channel);
        musicPlayer.playNextSong();
    }

    @Override
    public void loadFailed(FriendlyException exception) {
        messageSender.sendSafeCouldNotLoadSongMessage(channel);
        musicPlayer.playNextSong();
    }

    public void setChannel(TextChannel channel) {
        this.channel = channel;
    }

    public void setMusicPlayer(MusicPlayer musicPlayer) {
        this.musicPlayer = musicPlayer;
    }
}
