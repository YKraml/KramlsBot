package ui.embeds.anime;

import model.jikan.anime.animeByIdFull.AnimeFullById;
import model.jikan.anime.animeCharacters.AnimeCharacters;
import model.jikan.anime.animeCharacters.Datum;
import ui.embeds.DisplayableElement;
import ui.embeds.MyListEmbed;

import java.util.List;
import java.util.stream.Collectors;

public class CharacterListEmbed extends MyListEmbed<DisplayableElement> {

    public CharacterListEmbed(AnimeFullById anime, AnimeCharacters animeCharacters, int page) {
        super("Charaktere von '%s'".formatted(anime.getData().getTitleEnglish()),
                map(animeCharacters.getData()), page, true);
        this.setThumbnail(anime.getData().getImages().getJpg().getImageUrl());
    }

    private static List<DisplayableElement> map(List<Datum> list) {
        return list.stream().map(entry -> new DisplayableElement() {
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
        }).collect(Collectors.toList());
    }
}
