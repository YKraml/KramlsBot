package messages.messages;

import com.google.inject.Inject;
import discord.ChannelFinder;
import messages.MessageSender;
import model.jikan.anime.animeByIdFull.AnimeFullById;
import model.jikan.anime.animeThemes.AnimeThemes;
import music.audio.MusicPlayerManager;
import waifu.JikanFetcher;
import waifu.loader.PlayerLoader;
import youtube.YoutubeFetcher;

public class AnimeEndingsBuilder {

  private final PlayerLoader playerLoader;
  private final JikanFetcher jikanFetcher;
  private final ChannelFinder channelFinder;
  private final YoutubeFetcher youtubeFetcher;
  private final MusicPlayerManager musicPlayerManager;
  private final MessageSender messageSender;

  @Inject
  public AnimeEndingsBuilder(PlayerLoader playerLoader, JikanFetcher jikanFetcher,
      ChannelFinder channelFinder, YoutubeFetcher youtubeFetcher,
      MusicPlayerManager musicPlayerManager, MessageSender messageSender) {
    this.playerLoader = playerLoader;
    this.jikanFetcher = jikanFetcher;
    this.channelFinder = channelFinder;
    this.youtubeFetcher = youtubeFetcher;
    this.musicPlayerManager = musicPlayerManager;
    this.messageSender = messageSender;
  }

  public AnimeEndings createAnimeEndings(AnimeFullById anime, AnimeThemes animeThemes) {
    return new AnimeEndings(anime, animeThemes, playerLoader, jikanFetcher, channelFinder,
        youtubeFetcher, musicPlayerManager, messageSender);
  }
}