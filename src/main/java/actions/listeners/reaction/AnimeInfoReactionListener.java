package actions.listeners.reaction;

import discord.Emojis;
import exceptions.MyOwnException;
import messages.MessageSender;
import messages.messages.AnimeInformation;
import model.jikan.anime.animeByIdFull.AnimeFullById;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.listener.message.reaction.ReactionAddListener;
import waifu.JikanFetcher;

public class AnimeInfoReactionListener extends MyAbstractReactionListener implements
    ReactionAddListener {

  private final AnimeFullById anime;
  private final JikanFetcher jikanFetcher;
  private final MessageSender messageSender;
  private final AnimeOpeningEndingReactionListenerBuilder animeOpeningEndingReactionListenerBuilder;

  public AnimeInfoReactionListener(AnimeFullById anime, JikanFetcher jikanFetcher,
      MessageSender messageSender,
      AnimeOpeningEndingReactionListenerBuilder animeOpeningEndingReactionListenerBuilder) {
    this.anime = anime;
    this.jikanFetcher = jikanFetcher;
    this.messageSender = messageSender;
    this.animeOpeningEndingReactionListenerBuilder = animeOpeningEndingReactionListenerBuilder;
  }

  @Override
  protected void startRoutine(DiscordApi discordApi, Server server, TextChannel textChannel,
      Message message, User user, Emoji emoji) throws MyOwnException {
    if (emoji.equalsEmoji(Emojis.INFORMATION_SOURCE.getEmoji())) {
      messageSender.send(
          new AnimeInformation(anime, jikanFetcher, animeOpeningEndingReactionListenerBuilder),
          textChannel);
    }
  }
}
