package ui.messages.messages;

import domain.exceptions.MyOwnException;
import model.jikan.anime.animeByIdFull.AnimeFullById;
import model.jikan.anime.animeCharacters.Datum;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.embeds.anime.CharacterEmbed;
import ui.messages.MyMessageAbs;

public class CharacterOverview extends MyMessageAbs {

    private final AnimeFullById anime;
    private final Datum characterStaffEntry;

    public CharacterOverview(AnimeFullById anime, Datum characterStaffEntry) {
        this.anime = anime;
        this.characterStaffEntry = characterStaffEntry;
    }

    @Override
    public void startRoutine(Message message) throws MyOwnException {
        //Ignore.
    }

    @Override
    public EmbedBuilder getContent() throws MyOwnException {
        return new CharacterEmbed(anime, characterStaffEntry);
    }
}
