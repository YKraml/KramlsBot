package ui.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

class MyAudioEventListener extends AudioEventAdapter {

  private final MusicPlayer musicPlayer;

  public MyAudioEventListener(MusicPlayer musicPlayer) {
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
    musicPlayer.restartSong();
  }

  private void tryNextSong() {
    musicPlayer.playNextSong();
  }


}
