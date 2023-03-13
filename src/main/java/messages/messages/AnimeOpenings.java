package messages.messages;

import actions.listeners.reaction.AnimeStartSongReactionListener;
import discord.ChannelFinder;
import embeds.anime.AnimeSongEmbed;
import exceptions.MyOwnException;
import messages.MessageSender;
import messages.MyMessage;
import model.jikan.anime.animeByIdFull.AnimeFullById;
import model.jikan.anime.animeThemes.AnimeThemes;
import music.audio.MusicPlayerManager;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import waifu.JikanFetcher;
import waifu.loader.PlayerLoader;
import youtube.YoutubeFetcher;

public class AnimeOpenings extends MyMessage {

  private final AnimeFullById anime;
  private final AnimeThemes animeThemes;
  private final PlayerLoader playerLoader;
  private final JikanFetcher jikanFetcher;
  private final ChannelFinder channelFinder;
  private final YoutubeFetcher youtubeFetcher;
  private final MusicPlayerManager musicPlayerManager;
  private final MessageSender messageSender;

  public AnimeOpenings(AnimeFullById anime, AnimeThemes animeThemes, PlayerLoader playerLoader,
      JikanFetcher jikanFetcher, ChannelFinder channelFinder, YoutubeFetcher youtubeFetcher,
      MusicPlayerManager musicPlayerManager, MessageSender messageSender) {
    this.anime = anime;
    this.animeThemes = animeThemes;
    this.playerLoader = playerLoader;
    this.jikanFetcher = jikanFetcher;
    this.channelFinder = channelFinder;
    this.youtubeFetcher = youtubeFetcher;
    this.musicPlayerManager = musicPlayerManager;
    this.messageSender = messageSender;
  }

  @Override
  protected void startRoutine(Message message) throws MyOwnException {
    this.addCountEmojis(message, animeThemes.getData().getOpenings().size());
    message.addReactionAddListener(
        new AnimeStartSongReactionListener(anime, animeThemes.getData().getOpenings(), "Openings",
            channelFinder, youtubeFetcher, musicPlayerManager, playerLoader, jikanFetcher,
            messageSender));

  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
    return new AnimeSongEmbed("Openings", animeThemes.getData().getOpenings(), 0);
  }

}
