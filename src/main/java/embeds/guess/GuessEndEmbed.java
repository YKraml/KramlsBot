package embeds.guess;

import de.kraml.Main;
import java.util.Collection;
import model.jikan.anime.animeByIdFull.AnimeFullById;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class GuessEndEmbed extends EmbedBuilder {

    public GuessEndEmbed(AnimeFullById anime, String songNam, String url, Collection<String> possibleAnswers) {

        this.setTitle(anime.getData().getTitleEnglish());

        String answers = possibleAnswers.toString().length() > 1000 ? possibleAnswers.toString().substring(0, 900) : possibleAnswers.toString();
        this.addField("Moegliche Antworten", answers);

        this.addField("Gesuchter Song", songNam);

        this.setColor(Main.COLOR);

        this.setDescription(url);

        this.setUrl(anime.getData().getUrl());

        this.setThumbnail(anime.getData().getImages().getJpg().getImageUrl());

    }

}
