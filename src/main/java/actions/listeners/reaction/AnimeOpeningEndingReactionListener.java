package actions.listeners.reaction;

import actions.listeners.reaction.util.MyAbstractReactionListener;
import discord.ChannelFinder;
import discord.Emojis;
import exceptions.MyOwnException;
import messages.MessageSender;
import messages.messages.AnimeEndingsBuilder;
import messages.messages.AnimeOpeningsBuilder;
import model.jikan.anime.animeByIdFull.AnimeFullById;
import model.jikan.anime.animeThemes.AnimeThemes;
import music.audio.MusicPlayerManager;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.listener.message.reaction.ReactionAddListener;
import waifu.JikanFetcher;
import waifu.loader.PlayerLoader;
import youtube.YoutubeFetcher;

public class AnimeOpeningEndingReactionListener extends MyAbstractReactionListener implements
    ReactionAddListener {

  private final AnimeFullById anime;
  private final JikanFetcher jikanFetcher;
  private final MessageSender messageSender;
  private final AnimeOpeningsBuilder animeOpeningsBuilder;
  private final AnimeEndingsBuilder animeEndingsBuilder;

  public AnimeOpeningEndingReactionListener(AnimeFullById anime, JikanFetcher jikanFetcher,
      MessageSender messageSender, AnimeOpeningsBuilder animeOpeningsBuilder,
      AnimeEndingsBuilder animeEndingsBuilder) {
    this.anime = anime;
    this.jikanFetcher = jikanFetcher;
    this.messageSender = messageSender;
    this.animeOpeningsBuilder = animeOpeningsBuilder;
    this.animeEndingsBuilder = animeEndingsBuilder;
  }


  @Override
  protected void startRoutine(DiscordApi discordApi, Server server, TextChannel textChannel,
      Message message, User user, Emoji emoji) throws MyOwnException {

    AnimeThemes animeThemes = jikanFetcher.getAnimeThemes(anime);
    if (emoji.equalsEmoji(Emojis.MUSICAL_NOTE.getEmoji())) {
      messageSender.send(animeOpeningsBuilder.createAnimeOpenings(anime, animeThemes), textChannel);
    } else if (emoji.equalsEmoji(Emojis.NOTES.getEmoji())) {
      messageSender.send(animeEndingsBuilder.createAnimeEndings(anime, animeThemes), textChannel);
    }
  }


}
