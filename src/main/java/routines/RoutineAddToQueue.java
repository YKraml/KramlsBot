package routines;

import actions.listeners.commands.Answer;
import discord.ChannelFinder;
import exceptions.MyOwnException;
import exceptions.messages.PlaylistNotFound;
import exceptions.messages.YoutubeSearchEmpty;
import messages.MessageSender;
import messages.messages.SongQueueMessage;
import music.audio.MusicPlayerManager;
import music.audio.Queue;
import music.audio.QueueElement;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import waifu.loader.PlayerLoader;
import youtube.YoutubeFetcher;
import youtube.model.playlist.Item;
import youtube.model.playlist.Playlist;

public class RoutineAddToQueue extends Routine {

  private static final String TITLE_FILLER = "leer";

  private final MusicPlayerManager musicPlayerManager;
  private final YoutubeFetcher youtubeFetcher;
  private final MessageSender messageSender;
  private final ChannelFinder channelFinder;
  private final Server server;
  private final TextChannel channel;
  private final User user;
  private final String input;
  private final PlayerLoader playerLoader;

  public RoutineAddToQueue(Server server, TextChannel channel,
      User user, String input, MusicPlayerManager musicPlayerManager, YoutubeFetcher youtubeFetcher,
      MessageSender messageSender, ChannelFinder channelFinder, PlayerLoader playerLoader) {
    this.server = server;
    this.channel = channel;
    this.user = user;
    this.input = input;
    this.musicPlayerManager = musicPlayerManager;
    this.youtubeFetcher = youtubeFetcher;
    this.messageSender = messageSender;
    this.channelFinder = channelFinder;
    this.playerLoader = playerLoader;
  }


  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {

    ServerVoiceChannel voiceChannel = channelFinder.getServerVoiceChannelByMember(server, user);
    addToQueue(voiceChannel);

    musicPlayerManager.startPlaying(voiceChannel, channel);
    Queue queue = musicPlayerManager.getQueueByServer(server);
    messageSender.send(new SongQueueMessage(queue, musicPlayerManager, playerLoader), channel);

    return new Answer("Added Song to queue");
  }

  private void addToQueue(ServerVoiceChannel voiceChannel) throws MyOwnException {
    boolean songNameIsVideo = input.contains("watch?v=");
    boolean songNameIsPlaylist = input.contains("&list") || input.contains("?list");

    if (songNameIsVideo && !songNameIsPlaylist) {
      addOneSongToQueue(TITLE_FILLER, input, voiceChannel);
    } else if (songNameIsPlaylist) {
      addPlayListToQueue(input, voiceChannel);
    } else {
      searchVideoAndAddToQueue(voiceChannel);
    }
  }

  private void addOneSongToQueue(String title, String url, ServerVoiceChannel voiceChannel) {
    QueueElement queueElement = new QueueElement(title, url, user.getName());
    musicPlayerManager.addToQueue(server, voiceChannel, channel, queueElement);
  }

  private void addPlayListToQueue(String playlistUrl, ServerVoiceChannel voiceChannel)
      throws MyOwnException {

    String id = playlistUrl.split("list=")[1];
    Playlist playlist = youtubeFetcher.getPlayListById(id)
        .orElseThrow(() -> new MyOwnException(new PlaylistNotFound(playlistUrl), null));

    for (Item item : playlist.getItems()) {
      String title = item.getSnippet().getTitle();
      String videoId = item.getSnippet().getResourceId().getVideoId();
      String url = "https://www.youtube.com/watch?v=%s".formatted(videoId);
      addOneSongToQueue(url, title, voiceChannel);
    }
  }

  private void searchVideoAndAddToQueue(ServerVoiceChannel voiceChannel) throws MyOwnException {
    youtube.model.search.Item item = youtubeFetcher.getSearchByTitle(input + " song")
        .orElseThrow(() -> new MyOwnException(new YoutubeSearchEmpty(input), null))
        .getItems()
        .iterator()
        .next();

    String url = "https://www.youtube.com/watch?v=%s".formatted(item.getId().getVideoId());
    String title = item.getSnippet().getTitle();
    addOneSongToQueue(title, url, voiceChannel);

  }

}
