package embeds.anime;

import actions.listeners.reaction.Mapper;
import embeds.DisplayableElement;
import embeds.MyListEmbed;
import java.util.ArrayList;
import java.util.List;
import model.jikan.anime.animeByIdFull.AnimeFullById;
import model.jikan.anime.animeCharacters.AnimeCharacters;
import model.jikan.anime.animeCharacters.Datum;

public class CharacterListEmbed extends MyListEmbed<DisplayableElement> {

    public CharacterListEmbed(AnimeFullById anime, AnimeCharacters animeCharacters, int page) {
        super("Charaktere von \"" + anime.getData().getTitleEnglish() + "\"", ((Mapper<Datum>) list -> {

            List<DisplayableElement> elements = new ArrayList<>();
            list.forEach(entry -> elements.add(new DisplayableElement() {
                @Override
                public String getDisplayTitle() {
                    return entry.getCharacter().getName();
                }

                @Override
                public String getDisplayBody() {
                    return entry.getRole();
                }

                @Override
                public String getDisplayImageUrl() {
                    return entry.getCharacter().getImages().getJpg().getImageUrl();
                }
            }));

            return elements;
        }).map(animeCharacters.getData()), page, true);
        this.setThumbnail(anime.getData().getImages().getJpg().getImageUrl());
    }
}
