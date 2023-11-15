package logic.youtube;

import logic.youtube.model.playlist.Playlist;
import domain.exceptions.MyOwnException;
import domain.exceptions.messages.CouldNotFindVideo;
import java.text.MessageFormat;
import java.util.Optional;
import javax.inject.Singleton;

import logic.youtube.model.search.YoutubeSearch;

@Singleton
public class YoutubeFetcher {


  private final MyObjectMapper mapper;

  public YoutubeFetcher() {
    mapper = new MyObjectMapper();
  }


  public Optional<YoutubeSearch> getSearchByTitle(String title) throws MyOwnException {

    String fixedTitle = title.replaceAll(" ", "%20");
    String pattern = "https://youtube.googleapis.com/youtube/v3/search?part=snippet&q={0}&key=AIzaSyBBtGt-XPaS4U2Z5zDQ6sde4n8JD6f1avk";
    String data = APICaller.getData(MessageFormat.format(pattern, fixedTitle));

    YoutubeSearch search = mapper.map(data, YoutubeSearch.class);

    if (search.getItems() == null || search.getItems().isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(search);
  }

  public Optional<Playlist> getPlayListById(String id) throws MyOwnException {
    String pattern = "https://youtube.googleapis.com/youtube/v3/playlistItems?part=snippet%2CcontentDetails&maxResults=25&playlistId={0}&key=AIzaSyBBtGt-XPaS4U2Z5zDQ6sde4n8JD6f1avk";
    String data = APICaller.getData(MessageFormat.format(pattern, id));
    return Optional.ofNullable(mapper.map(data, Playlist.class));
  }

  public String getIdByVideoName(String name) throws MyOwnException {

    Optional<YoutubeSearch> search = this.getSearchByTitle(name);
    if (search.isEmpty()) {
      throw new MyOwnException(new CouldNotFindVideo(name), null);
    }

    return search.get().getItems().iterator().next().getId().getVideoId();
  }

}
