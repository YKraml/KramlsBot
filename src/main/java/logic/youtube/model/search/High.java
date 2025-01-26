package logic.youtube.model.search;

public class High {

  private String url;
  private Integer width;
  private Integer height;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public High withUrl(String url) {
    this.url = url;
    return this;
  }

  public Integer getWidth() {
    return width;
  }

  public void setWidth(Integer width) {
    this.width = width;
  }

  public High withWidth(Integer width) {
    this.width = width;
    return this;
  }

  public Integer getHeight() {
    return height;
  }

  public void setHeight(Integer height) {
    this.height = height;
  }

  public High withHeight(Integer height) {
    this.height = height;
    return this;
  }

}
