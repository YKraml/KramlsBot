package logic.music.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import ui.embeds.ExceptionEmbed;
import domain.exceptions.MyOwnException;
import org.javacord.api.entity.channel.TextChannel;

class MyAudioLoadResultListener implements AudioLoadResultHandler {

  private final AudioPlayer player;
  private TextChannel channel;
  private MusicPlayer musicPlayer;

  public MyAudioLoadResultListener(AudioPlayer player, TextChannel channel) {
    this.player = player;
    this.channel = channel;
  }

  @Override
  public void trackLoaded(AudioTrack track) {
    musicPlayer.updateMessages();
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
    channel.sendMessage(new ExceptionEmbed(new MyOwnException(() -> "Kein Treffer für die Url.", null)));
    musicPlayer.playNextSong();
  }

  @Override
  public void loadFailed(FriendlyException exception) {
    channel.sendMessage(new ExceptionEmbed(new MyOwnException(() -> "Laden fehlgeschlagen.", null)));
    musicPlayer.playNextSong();
  }

  public void setChannel(TextChannel channel) {
    this.channel = channel;
  }

  public void setMusicPlayer(MusicPlayer musicPlayer) {
    this.musicPlayer = musicPlayer;
  }
}
