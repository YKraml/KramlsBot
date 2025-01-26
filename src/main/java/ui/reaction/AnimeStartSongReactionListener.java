package ui.reaction;

import domain.DisplayableElement;
import domain.exceptions.MyOwnException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import logic.routines.RoutineAddToQueueBuilder;
import logic.routines.RoutineRunner;
import logic.waifu.JikanFetcher;
import model.jikan.anime.animeByIdFull.AnimeFullById;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.listener.message.reaction.ReactionAddListener;
import ui.embeds.anime.AnimeSongEmbed;

public class AnimeStartSongReactionListener extends
    MyAbstractListListener<DisplayableElement> implements ReactionAddListener {

  private final List<String> songs;
  private final AnimeFullById anime;
  private final String type;
  private final JikanFetcher jikanFetcher;
  private final RoutineAddToQueueBuilder routineBuilder;
  private final RoutineRunner routineRunner;

  public AnimeStartSongReactionListener(AnimeFullById anime, List<String> songs, String type,
      JikanFetcher jikanFetcher, RoutineAddToQueueBuilder routineBuilder,
      RoutineRunner routineRunner) {
    super(mapSongs(songs));
    this.songs = songs;
    this.anime = anime;
    this.type = type;
    this.jikanFetcher = jikanFetcher;
    this.routineBuilder = routineBuilder;
    this.routineRunner = routineRunner;
  }

  private static List<DisplayableElement> mapSongs(List<String> songs) {
    return songs.stream().map((Function<String, DisplayableElement>) s -> new DisplayableElement() {
      @Override
      public String getDisplayTitle() {
        return s;
      }

      @Override
      public String getDisplayBody() {
        return s;
      }

      @Override
      public String getDisplayImageUrl() {
        return "";
      }
    }).collect(Collectors.toList());
  }


  @Override
  protected void updateMessage(Message message, int page) throws MyOwnException {
    message.edit(new AnimeSongEmbed(type, songs, page));
  }

  @Override
  protected void reactToCountEmoji(Server server, TextChannel textChannel, User user,
      int listPosition) throws MyOwnException {
    String animeTitle = this.anime.getData().getTitleEnglish();
    String song = songs.get(listPosition);
    String titleToBeSearched = jikanFetcher.parseOpeningName(animeTitle, song);
    routineRunner.start(
        routineBuilder.createRoutineAddToQueue(server, textChannel, user, titleToBeSearched));
  }

  @Override
  protected void reactToTooHighCountEmoji(TextChannel textChannel, int listPosition)
      throws MyOwnException {
    //This just ignore;
  }

  @Override
  protected void startRoutine(DiscordApi discordApi, Server server, TextChannel textChannel,
      Message message, User user, Emoji emoji) throws MyOwnException {
    super.startRoutine(discordApi, server, textChannel, message, user, emoji);


  }
}
