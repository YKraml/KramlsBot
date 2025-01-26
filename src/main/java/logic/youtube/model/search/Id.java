package logic.youtube.model.search;

public class Id {

  private String kind;
  private String videoId;

  public String getKind() {
    return kind;
  }

  public void setKind(String kind) {
    this.kind = kind;
  }

  public Id withKind(String kind) {
    this.kind = kind;
    return this;
  }

  public String getVideoId() {
    return videoId;
  }

  public void setVideoId(String videoId) {
    this.videoId = videoId;
  }

  public Id withVideoId(String videoId) {
    this.videoId = videoId;
    return this;
  }

}
