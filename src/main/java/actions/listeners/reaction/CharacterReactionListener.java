package actions.listeners.reaction;

import actions.listeners.reaction.util.MyAbstractReactionListener;
import discord.Emojis;
import exceptions.MyOwnException;
import java.util.Comparator;
import messages.MessageSenderImpl;
import messages.messages.CharacterList;
import model.jikan.anime.animeByIdFull.AnimeFullById;
import model.jikan.anime.animeCharacters.AnimeCharacters;
import model.jikan.anime.animeCharacters.Datum;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.listener.message.reaction.ReactionAddListener;
import waifu.JikanFetcher;

public class CharacterReactionListener extends MyAbstractReactionListener implements
    ReactionAddListener {

  private final AnimeFullById anime;
  private final JikanFetcher jikanFetcher;

  public CharacterReactionListener(AnimeFullById anime, JikanFetcher jikanFetcher) {
    this.anime = anime;
    this.jikanFetcher = jikanFetcher;
  }

  @Override
  protected void startRoutine(DiscordApi discordApi, Server server, TextChannel textChannel,
      Message message, User user, Emoji emoji) throws MyOwnException {

    AnimeCharacters animeCharacters = jikanFetcher.getAnimeCharacters(anime);

    if (emoji.equalsEmoji(Emojis.MAGE.getEmoji())) {
      Comparator<Datum> comparator = Comparator.comparing(Datum::getRole)
          .thenComparing(o -> o.getCharacter().getName());
      animeCharacters.getData().sort(comparator);
      MessageSenderImpl result;
      synchronized (MessageSenderImpl.class) {
        result = new MessageSenderImpl();
      }
      result.send(new CharacterList(animeCharacters, anime), textChannel);
    }
  }

}
