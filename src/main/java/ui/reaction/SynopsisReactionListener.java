package ui.reaction;

import domain.Emojis;
import domain.exceptions.MyOwnException;
import logic.messages.MessageSender;
import model.jikan.anime.animeByIdFull.AnimeFullById;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.listener.message.reaction.ReactionAddListener;
import ui.messages.messages.AnimeSynopsis;

public class SynopsisReactionListener extends MyAbstractReactionListener implements
    ReactionAddListener {


  private final AnimeFullById anime;
  private final MessageSender messageSender;

  public SynopsisReactionListener(AnimeFullById anime, MessageSender messageSender) {
    this.anime = anime;
    this.messageSender = messageSender;
  }


  @Override
  public void startRoutine(DiscordApi discordApi, Server server, TextChannel textChannel,
      Message message, User user, Emoji emoji) throws MyOwnException {
    if (emoji.equalsEmoji(Emojis.INFORMATION_SOURCE.getEmoji())) {
      messageSender.send(new AnimeSynopsis(anime), textChannel);
    }
  }
}
