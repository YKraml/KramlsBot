package ui.reaction;

import domain.exceptions.MyOwnException;
import logic.waifu.JikanFetcher;
import model.jikan.anime.animeByIdFull.AnimeFullById;
import model.jikan.anime.animeCharacters.AnimeCharacters;
import model.jikan.anime.animeCharacters.Datum;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.listener.message.reaction.ReactionAddListener;
import logic.MessageSender;
import ui.messages.MessageSenderImpl;
import ui.messages.messages.CharacterList;
import util.Emojis;

import java.util.Comparator;

public class CharacterReactionListener extends MyAbstractReactionListener implements
        ReactionAddListener {

    private final AnimeFullById anime;
    private final JikanFetcher jikanFetcher;
    private final MessageSender messageSender;

    public CharacterReactionListener(AnimeFullById anime, JikanFetcher jikanFetcher,
                                     MessageSender messageSender) {
        this.anime = anime;
        this.jikanFetcher = jikanFetcher;
        this.messageSender = messageSender;
    }

    @Override
    protected void startRoutine(DiscordApi discordApi, Server server, TextChannel textChannel,
                                Message message, User user, Emoji emoji) throws MyOwnException {

        AnimeCharacters animeCharacters = jikanFetcher.getAnimeCharacters(anime);

        if (emoji.equalsEmoji(Emojis.MAGE.getEmoji())) {
            Comparator<Datum> comparator = Comparator.comparing(Datum::getRole)
                    .thenComparing(o -> o.getCharacter().getName());
            animeCharacters.getData().sort(comparator);
            MessageSenderImpl result;
            synchronized (MessageSenderImpl.class) {
                result = new MessageSenderImpl();
            }
            result.send(new CharacterList(animeCharacters, anime, messageSender), textChannel);
        }
    }

}
