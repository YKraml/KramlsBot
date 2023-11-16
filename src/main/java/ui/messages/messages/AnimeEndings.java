package ui.messages.messages;

import domain.exceptions.MyOwnException;
import model.jikan.anime.animeByIdFull.AnimeFullById;
import model.jikan.anime.animeThemes.AnimeThemes;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.embeds.anime.AnimeSongEmbed;
import ui.messages.MyMessageAbs;
import ui.reaction.AnimeStartSongReactionListenerBuilder;

public class AnimeEndings extends MyMessageAbs {

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
    public void startRoutine(Message message) throws MyOwnException {
        this.addCountEmojis(message, animeThemes.getData().getEndings().size());
        message.addReactionAddListener(
                animeStartSongReactionListenerBuilder.createAnimeStartSongReactionListener(anime,
                        anime.getData().getTheme()
                                .getEndings(), "Endings"));
    }

    @Override
    public EmbedBuilder getContent() throws MyOwnException {
        return new AnimeSongEmbed("Openings", animeThemes.getData().getEndings(), 0);

    }
}
