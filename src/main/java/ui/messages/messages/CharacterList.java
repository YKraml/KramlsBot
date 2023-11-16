package ui.messages.messages;

import domain.exceptions.MyOwnException;
import model.jikan.anime.animeByIdFull.AnimeFullById;
import model.jikan.anime.animeCharacters.AnimeCharacters;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.embeds.anime.CharacterListEmbed;
import ui.messages.MessageSender;
import ui.messages.MyMessage;
import ui.reaction.CharacterListListener;

public class CharacterList extends MyMessage {
    private final AnimeCharacters animeCharacters;
    private final AnimeFullById anime;
    private final MessageSender messageSender;

    public CharacterList(AnimeCharacters animeCharacters, AnimeFullById anime,
                         MessageSender messageSender) {
        this.animeCharacters = animeCharacters;
        this.anime = anime;
        this.messageSender = messageSender;
    }

    @Override
    protected void startRoutine(Message message) throws MyOwnException {
        this.addCountEmojis(message, animeCharacters.getData().size());
        message.addReactionAddListener(new CharacterListListener(anime, animeCharacters,
                messageSender));
    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        return new CharacterListEmbed(anime, animeCharacters, 0);
    }
}
