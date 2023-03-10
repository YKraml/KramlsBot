package actions.listeners.reaction;

import discord.ChannelFinder;
import embeds.DisplayableElement;
import embeds.anime.AnimeSongEmbed;
import exceptions.MyOwnException;
import exceptions.messages.CouldNotFindSong;
import java.util.function.Function;
import java.util.stream.Collectors;
import messages.MessageSender;
import messages.messages.SongAdded;
import model.jikan.anime.animeByIdFull.AnimeFullById;
import music.audio.MusicPlayerManager;
import music.queue.QueueElement;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.listener.message.reaction.ReactionAddListener;
import waifu.JikanFetcher;
import waifu.loader.PlayerLoader;
import youtube.YoutubeFetcher;
import youtube.model.search.YoutubeSearch;

import java.util.List;
import java.util.Optional;

public class AnimeStartSongReactionListener extends
    MyAbstractListListener<DisplayableElement> implements
    ReactionAddListener {

  private final List<String> songs;
  private final AnimeFullById anime;
  private final String type;
  private final ChannelFinder channelFinder;
  private final YoutubeFetcher youtubeFetcher;
  private final MusicPlayerManager musicPlayerManager;
  private final PlayerLoader playerLoader;
  private final JikanFetcher jikanFetcher;
  private final MessageSender messageSender;

  public AnimeStartSongReactionListener(AnimeFullById anime, List<String> songs, String type,
      ChannelFinder channelFinder, YoutubeFetcher youtubeFetcher,
      MusicPlayerManager musicPlayerManager, PlayerLoader playerLoader, JikanFetcher jikanFetcher,
      MessageSender messageSender) {
    super(mapSongs(songs));
    this.songs = songs;
    this.anime = anime;
    this.type = type;
    this.channelFinder = channelFinder;
    this.youtubeFetcher = youtubeFetcher;
    this.musicPlayerManager = musicPlayerManager;
    this.playerLoader = playerLoader;
    this.jikanFetcher = jikanFetcher;
    this.messageSender = messageSender;
  }

  private static List<DisplayableElement> mapSongs(List<String> songs) {
    return songs.stream()
        .map((Function<String, DisplayableElement>) s -> new DisplayableElement() {
          @Override
          public String getDisplayTitle() {
            return s;
          }

          @Override
          public String getDisplayBody() {
            return s;
          }

          @Override
          public String getDisplayImageUrl() {
            return "";
          }
        }).collect(Collectors.toList());
  }


  @Override
  protected void updateMessage(Message message, int page) throws MyOwnException {
    message.edit(new AnimeSongEmbed(type, songs, page));
  }

  @Override
  protected void reactToCountEmoji(TextChannel textChannel, int listPosition, Server server,
      User user)
      throws MyOwnException {
    String animeTitle = this.anime.getData().getTitleEnglish();
    String song = songs.get(listPosition);
    String titleToBeSearched = jikanFetcher.parseOpeningName(animeTitle, song);

    Optional<YoutubeSearch> youtubeSearch = youtubeFetcher.getSearchByTitle(titleToBeSearched);
    if (youtubeSearch.isEmpty()) {
      throw new MyOwnException(new CouldNotFindSong(titleToBeSearched), null);
    }

    String videoId = youtubeSearch.get().getItems().get(0).getId().getVideoId();
    String url = "https://www.youtube.com/watch?v=" + videoId;
    QueueElement queueElement = new QueueElement(titleToBeSearched, url, user.getName());
    ServerVoiceChannel voiceChannel = channelFinder.getServerVoiceChannelByMember(server, user);
    musicPlayerManager.addToQueue(server, voiceChannel, textChannel, queueElement);
    musicPlayerManager.startPlaying(voiceChannel, textChannel);
    messageSender.send(new SongAdded(queueElement, musicPlayerManager, playerLoader), textChannel);
  }

  @Override
  protected void reactToTooHighCountEmoji(TextChannel textChannel, int listPosition)
      throws MyOwnException {

  }

  @Override
  protected void startRoutine(DiscordApi discordApi, Server server, TextChannel textChannel,
      Message message, User user, Emoji emoji) throws MyOwnException {
    super.startRoutine(discordApi, server, textChannel, message, user, emoji);


  }
}
