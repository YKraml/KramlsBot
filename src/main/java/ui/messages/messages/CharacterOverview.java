package ui.messages.messages;

import domain.exceptions.MyOwnException;
import model.jikan.anime.animeByIdFull.AnimeFullById;
import model.jikan.anime.animeCharacters.Datum;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.embeds.anime.CharacterEmbed;
import ui.messages.MyMessage;

public class CharacterOverview extends MyMessage {

    private final AnimeFullById anime;
    private final Datum characterStaffEntry;

    public CharacterOverview(AnimeFullById anime, Datum characterStaffEntry) {
        this.anime = anime;
        this.characterStaffEntry = characterStaffEntry;
    }

    @Override
    protected void startRoutine(Message message) throws MyOwnException {
        //Ignore.
    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        return new CharacterEmbed(anime, characterStaffEntry);
    }
}
