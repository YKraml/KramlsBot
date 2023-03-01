package music.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import exceptions.MyOwnException;
import de.kraml.Terminal;

class MyAudioEventAdapter extends AudioEventAdapter {

    private final MusicPlayer musicPlayer;

    public MyAudioEventAdapter(MusicPlayer musicPlayer) {
        this.musicPlayer = musicPlayer;
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {

        if (endReason.equals(AudioTrackEndReason.FINISHED)) {
            this.tryNextSong();
        }
    }

    @Override
    public void onTrackException(AudioPlayer player, AudioTrack track, FriendlyException exception) {
        this.tryCurrentSong();
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        super.onTrackStart(player, track);
    }

    @Override
    public void onTrackStuck(AudioPlayer player, AudioTrack track, long thresholdMs) {
        this.tryCurrentSong();
    }

    private void tryCurrentSong() {
        musicPlayer.playCurrentSong();
    }

    private void tryNextSong() {
        try {
            musicPlayer.playNextSong();
        } catch (MyOwnException e) {
            Terminal.printError(e.getExceptionMessage().getContent());
        }
    }


}
