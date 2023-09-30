package messages.messages;

import actions.listeners.reaction.AnimeStartSongReactionListenerBuilder;
import embeds.anime.AnimeSongEmbed;
import exceptions.MyOwnException;
import messages.MyMessage;
import model.jikan.anime.animeByIdFull.AnimeFullById;
import model.jikan.anime.animeThemes.AnimeThemes;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class AnimeEndings extends MyMessage {

  private final AnimeFullById anime;
  private final AnimeThemes animeThemes;
  private final AnimeStartSongReactionListenerBuilder animeStartSongReactionListenerBuilder;

  public AnimeEndings(AnimeFullById anime, AnimeThemes animeThemes,
      AnimeStartSongReactionListenerBuilder animeStartSongReactionListenerBuilder) {
    this.anime = anime;
    this.animeThemes = animeThemes;
    this.animeStartSongReactionListenerBuilder = animeStartSongReactionListenerBuilder;
  }


  @Override
  protected void startRoutine(Message message) throws MyOwnException {
    this.addCountEmojis(message, animeThemes.getData().getEndings().size());
    message.addReactionAddListener(
        animeStartSongReactionListenerBuilder.createAnimeStartSongReactionListener(anime,
            anime.getData().getTheme()
                .getEndings(), "Endings"));
  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
    return new AnimeSongEmbed("Openings", animeThemes.getData().getEndings(), 0);

  }
}
