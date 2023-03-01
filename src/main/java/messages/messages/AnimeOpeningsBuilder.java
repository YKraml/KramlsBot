package messages.messages;

import discord.ChannelFinder;
import messages.MessageSender;
import model.jikan.anime.animeByIdFull.AnimeFullById;
import model.jikan.anime.animeThemes.AnimeThemes;
import music.audio.MusicPlayerManager;
import waifu.JikanFetcher;
import waifu.loader.PlayerLoader;
import youtube.YoutubeFetcher;

public class AnimeOpeningsBuilder {

  private final PlayerLoader playerLoader;
  private final JikanFetcher jikanFetcher;
  private final ChannelFinder channelFinder;
  private final YoutubeFetcher youtubeFetcher;
  private final MusicPlayerManager musicPlayerManager;
  private final MessageSender messageSender;

  public AnimeOpeningsBuilder(PlayerLoader playerLoader,
      JikanFetcher jikanFetcher, ChannelFinder channelFinder, YoutubeFetcher youtubeFetcher,
      MusicPlayerManager musicPlayerManager, MessageSender messageSender) {
    this.playerLoader = playerLoader;
    this.jikanFetcher = jikanFetcher;
    this.channelFinder = channelFinder;
    this.youtubeFetcher = youtubeFetcher;
    this.musicPlayerManager = musicPlayerManager;
    this.messageSender = messageSender;
  }


  public AnimeOpenings createAnimeOpenings(AnimeFullById anime, AnimeThemes animeThemes) {
    return new AnimeOpenings(anime, animeThemes, playerLoader, jikanFetcher, channelFinder,
        youtubeFetcher, musicPlayerManager, messageSender);
  }
}