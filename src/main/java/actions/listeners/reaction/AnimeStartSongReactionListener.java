package actions.listeners.reaction;

import discord.ChannelFinder;
import discord.Emojis;
import embeds.anime.AnimeSongEmbed;
import exceptions.MyOwnException;
import exceptions.messages.CouldNotFindSong;
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

public class AnimeStartSongReactionListener extends MyAbstractReactionListener implements
    ReactionAddListener {

  private final List<String> songs;
  private final AnimeFullById anime;
  private final String type;
  private int pageNumber = 0;
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


  @Override
  protected void startRoutine(DiscordApi discordApi, Server server, TextChannel textChannel,
      Message message, User user, Emoji emoji) throws MyOwnException {

    for (int i = 0; i < Emojis.getCountEmojis().length; i++) {

      if (emoji.equalsEmoji(Emojis.getCountEmojis()[i])) {

        ServerVoiceChannel voiceChannel = channelFinder.getServerVoiceChannelByMember(server, user);

        if (songs.size() <= i + 10 * this.pageNumber) {
          textChannel.sendMessage("Diesen Song gibt es nicht.");

          return;
        }

        String animeTitle = this.anime.getData().getTitleEnglish();
        String song = songs.get(i + 10 * this.pageNumber);
        String titleToBeSearched = jikanFetcher.parseOpeningName(animeTitle, song);

        Optional<YoutubeSearch> youtubeSearch = youtubeFetcher.getSearchByTitle(titleToBeSearched);
        if (youtubeSearch.isEmpty()) {
          throw new MyOwnException(new CouldNotFindSong(titleToBeSearched), null);
        }

        String videoId = youtubeSearch.get().getItems().get(0).getId().getVideoId();
        String url = "https://www.youtube.com/watch?v=" + videoId;
        QueueElement queueElement = new QueueElement(titleToBeSearched, url, user.getName());
        musicPlayerManager.addToQueue(server, voiceChannel, textChannel, queueElement);
        musicPlayerManager.startPlaying(voiceChannel, textChannel);
        messageSender.send(new SongAdded(queueElement, musicPlayerManager, playerLoader), textChannel);
        return;
      }
    }

    int pages = this.songs.size() / 10;
    if (this.songs.size() % 10 != 0) {
      pages++;
    }

    if (emoji.equalsEmoji(Emojis.REWIND.getEmoji())) {
      pageNumber--;
      pageNumber = (pageNumber + pages) % pages;
      message.edit(new AnimeSongEmbed(this.type, this.songs, this.pageNumber));
    } else if (emoji.equalsEmoji(Emojis.FAST_FORWARD.getEmoji())) {
      pageNumber++;
      pageNumber = (pageNumber + pages) % pages;
      message.edit(new AnimeSongEmbed(this.type, this.songs, this.pageNumber));
    }

  }
}
