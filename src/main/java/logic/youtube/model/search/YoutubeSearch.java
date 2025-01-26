package logic.youtube.model.search;

import java.util.List;

public class YoutubeSearch {

  private String kind;
  private String etag;
  private String nextPageToken;
  private String regionCode;
  private PageInfo pageInfo;
  private List<Item> items = null;

  public String getKind() {
    return kind;
  }

  public void setKind(String kind) {
    this.kind = kind;
  }

  public YoutubeSearch withKind(String kind) {
    this.kind = kind;
    return this;
  }

  public String getEtag() {
    return etag;
  }

  public void setEtag(String etag) {
    this.etag = etag;
  }

  public YoutubeSearch withEtag(String etag) {
    this.etag = etag;
    return this;
  }

  public String getNextPageToken() {
    return nextPageToken;
  }

  public void setNextPageToken(String nextPageToken) {
    this.nextPageToken = nextPageToken;
  }

  public YoutubeSearch withNextPageToken(String nextPageToken) {
    this.nextPageToken = nextPageToken;
    return this;
  }

  public String getRegionCode() {
    return regionCode;
  }

  public void setRegionCode(String regionCode) {
    this.regionCode = regionCode;
  }

  public YoutubeSearch withRegionCode(String regionCode) {
    this.regionCode = regionCode;
    return this;
  }

  public PageInfo getPageInfo() {
    return pageInfo;
  }

  public void setPageInfo(PageInfo pageInfo) {
    this.pageInfo = pageInfo;
  }

  public YoutubeSearch withPageInfo(PageInfo pageInfo) {
    this.pageInfo = pageInfo;
    return this;
  }

  public List<Item> getItems() {
    return items;
  }

  public void setItems(List<Item> items) {
    this.items = items;
  }

  public YoutubeSearch withItems(List<Item> items) {
    this.items = items;
    return this;
  }

}
