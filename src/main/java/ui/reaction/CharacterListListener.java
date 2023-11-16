package ui.reaction;

import domain.DisplayableElement;
import domain.exceptions.MyOwnException;
import logic.MessageSender;
import model.jikan.anime.animeByIdFull.AnimeFullById;
import model.jikan.anime.animeCharacters.AnimeCharacters;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import ui.embeds.anime.CharacterListEmbed;
import ui.messages.messages.CharacterOverview;

import java.util.List;
import java.util.stream.Collectors;

public class CharacterListListener extends MyAbstractListListener<DisplayableElement> {

    private final AnimeFullById anime;
    private final AnimeCharacters characterStaff;
    private final MessageSender messageSender;


    public CharacterListListener(AnimeFullById anime, AnimeCharacters animeCharacters,
                                 MessageSender messageSender) {
        super(map(animeCharacters));
        this.anime = anime;
        this.characterStaff = animeCharacters;
        this.messageSender = messageSender;
    }

    private static List<DisplayableElement> map(AnimeCharacters animeCharacters) {
        return animeCharacters.getData().stream().map(entry -> new DisplayableElement() {
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

    @Override
    protected void updateMessage(Message message, int page) {
        message.edit(new CharacterListEmbed(anime, characterStaff, page));
    }

    @Override
    protected void reactToCountEmoji(Server server, TextChannel textChannel, User user,
                                     int listPosition) throws MyOwnException {
        messageSender.send(new CharacterOverview(anime, characterStaff.getData().get(listPosition)),
                textChannel);
    }

    @Override
    protected void reactToTooHighCountEmoji(TextChannel textChannel, int listPosition) {
        //Just ignore.
    }

}
