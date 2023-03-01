package music.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import de.kraml.Terminal;
import embeds.ExceptionEmbed;
import exceptions.MyOwnException;
import org.javacord.api.entity.channel.TextChannel;

class MyAudioLoadResultHandler implements AudioLoadResultHandler {

  private final AudioPlayer player;
  private TextChannel channel;
  private final MusicPlayer musicPlayer;

  public MyAudioLoadResultHandler(AudioPlayer player, TextChannel channel,
      MusicPlayer musicPlayer) {
    this.player = player;
    this.channel = channel;
    this.musicPlayer = musicPlayer;
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
    channel.sendMessage(new ExceptionEmbed(new MyOwnException(() -> "No match for Url", null)));
    try {
      musicPlayer.playNextSong();
    } catch (MyOwnException e) {
      Terminal.printError(e.getExceptionMessage().getContent());
    }
  }

  @Override
  public void loadFailed(FriendlyException exception) {
    channel.sendMessage("Could not play song, because the load failed");
    try {
      musicPlayer.playNextSong();
    } catch (MyOwnException e) {
      Terminal.printError(e.getExceptionMessage().getContent());
    }
  }

  public void setChannel(TextChannel channel) {
    this.channel = channel;
  }
}
