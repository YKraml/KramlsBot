package logic.youtube.model.playlist;

import java.util.List;

public class Playlist {

  public String kind;
  public String etag;
  public List<Item> items;
  public PageInfo pageInfo;


  public String getKind() {
    return kind;
  }

  public String getEtag() {
    return etag;
  }

  public List<Item> getItems() {
    return items;
  }

  public PageInfo getPageInfo() {
    return pageInfo;
  }
}
