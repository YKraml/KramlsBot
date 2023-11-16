package ui.messages.messages;

import domain.exceptions.MyOwnException;
import model.jikan.anime.animeByIdFull.AnimeFullById;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.embeds.anime.AnimeSynopsisEmbed;
import ui.messages.MyMessageAbs;

public class AnimeSynopsis extends MyMessageAbs {

    private final AnimeFullById anime;

    public AnimeSynopsis(AnimeFullById anime) {
        this.anime = anime;
    }

    @Override
    public void startRoutine(Message message) throws MyOwnException {
        //Just ignore.
    }

    @Override
    public EmbedBuilder getContent() throws MyOwnException {
        return new AnimeSynopsisEmbed(anime);
    }
}
