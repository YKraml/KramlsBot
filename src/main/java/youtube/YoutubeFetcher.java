package youtube;

import java.text.MessageFormat;
import youtube.model.playlist.Playlist;
import youtube.model.search.YoutubeSearch;
import exceptions.MyOwnException;
import exceptions.messages.CouldNotFindVideo;

import java.util.Optional;


public class YoutubeFetcher {


  private final MyObjectMapper om;

  public YoutubeFetcher() {
    om = new MyObjectMapper();
  }


  public Optional<YoutubeSearch> getSearchByTitle(String title) throws MyOwnException {

    String fixedTitle = title.replaceAll(" ", "%20");
    String pattern = "https://youtube.googleapis.com/youtube/v3/search?part=snippet&q={0}&key=AIzaSyBBtGt-XPaS4U2Z5zDQ6sde4n8JD6f1avk";
    String data = APICaller.getData(MessageFormat.format(pattern, fixedTitle));

    Optional<YoutubeSearch> search = om.map(data, YoutubeSearch.class);

    if (search.isPresent() && (search.get().getItems() == null || search.get().getItems().isEmpty())) {
      return Optional.empty();
    }
    return search;
  }

  public Optional<Playlist> getPlayListById(String id) throws MyOwnException {
    String pattern = "https://youtube.googleapis.com/youtube/v3/playlistItems?part=snippet%2CcontentDetails&maxResults=25&playlistId={0}&key=AIzaSyBBtGt-XPaS4U2Z5zDQ6sde4n8JD6f1avk";
    String data = APICaller.getData(MessageFormat.format(pattern, id));
    return om.map(data, Playlist.class);
  }

  public String getIdByVideoName(String name) throws MyOwnException {

    Optional<YoutubeSearch> search = this.getSearchByTitle(name);
    if (search.isEmpty()) {
      throw new MyOwnException(new CouldNotFindVideo(name), null);
    }

    return search.get().getItems().iterator().next().getId().getVideoId();
  }

}
