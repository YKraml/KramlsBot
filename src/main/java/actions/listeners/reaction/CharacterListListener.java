package actions.listeners.reaction;

import embeds.DisplayableElement;
import embeds.anime.CharacterListEmbed;
import exceptions.MyOwnException;
import messages.MessageSenderImpl;
import messages.messages.CharacterOverview;
import model.jikan.anime.animeByIdFull.AnimeFullById;
import model.jikan.anime.animeCharacters.AnimeCharacters;
import model.jikan.anime.animeCharacters.Datum;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;

import java.util.ArrayList;
import java.util.List;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class CharacterListListener extends MyAbstractListListener<DisplayableElement> {

  private final AnimeFullById anime;
  private final AnimeCharacters characterStaff;


  public CharacterListListener(AnimeFullById anime, AnimeCharacters animeCharacters) {
    super(((Mapper<Datum>) list -> {

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
    }).map(animeCharacters.getData()));
    this.anime = anime;
    this.characterStaff = animeCharacters;
  }

  @Override
  protected void updateMessage(Message message, int page) {
    message.edit(new CharacterListEmbed(anime, characterStaff, page));
  }

  @Override
  protected void reactToCountEmoji(Server server, TextChannel textChannel, User user,
      int listPosition) throws MyOwnException {
    MessageSenderImpl result;
    synchronized (MessageSenderImpl.class) {
      result = new MessageSenderImpl();
    }
    result.send(new CharacterOverview(anime, characterStaff.getData().get(listPosition)),
        textChannel);
  }

  @Override
  protected void reactToTooHighCountEmoji(TextChannel textChannel, int listPosition) {
    //Just ignore.
  }

}
