package ui.messages.messages;

import domain.Emojis;
import domain.exceptions.MyOwnException;
import logic.messages.MessageSender;
import logic.waifu.JikanFetcher;
import model.jikan.anime.animeByIdFull.AnimeFullById;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.embeds.anime.AnimeEmbed;
import ui.messages.MyMessageAbs;
import ui.reaction.AnimeOpeningEndingReactionListenerBuilder;
import ui.reaction.CharacterReactionListener;
import ui.reaction.SynopsisReactionListener;

public class AnimeInformation extends MyMessageAbs {

  private final AnimeFullById anime;
  private final JikanFetcher jikanFetcher;
  private final AnimeOpeningEndingReactionListenerBuilder animeOpeningEndingReactionListenerBuilder;
  private final MessageSender messageSender;

  public AnimeInformation(AnimeFullById anime, JikanFetcher jikanFetcher,
      AnimeOpeningEndingReactionListenerBuilder animeOpeningEndingReactionListenerBuilder,
      MessageSender messageSender) {
    this.anime = anime;
    this.jikanFetcher = jikanFetcher;
    this.animeOpeningEndingReactionListenerBuilder = animeOpeningEndingReactionListenerBuilder;
    this.messageSender = messageSender;
  }

  @Override
  public void startRoutine(Message message) throws MyOwnException {
    message.addReaction(Emojis.MUSICAL_NOTE.getEmoji());
    message.addReaction(Emojis.NOTES.getEmoji());
    message.addReaction(Emojis.INFORMATION_SOURCE.getEmoji());
    message.addReaction(Emojis.MAGE.getEmoji());
    message.addReactionAddListener(
        animeOpeningEndingReactionListenerBuilder.createAnimeOpeningEndingReactionListener(anime));
    message.addReactionAddListener(new SynopsisReactionListener(anime, messageSender));
    message.addReactionAddListener(
        new CharacterReactionListener(anime, jikanFetcher, messageSender));
  }

  @Override
  public EmbedBuilder getContent() throws MyOwnException {
    return new AnimeEmbed(anime);
  }


}
