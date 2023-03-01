package youtube.model.playlist;
public class Item{
    public String kind;
    public String etag;
    public String id;
    public Snippet snippet;
    public ContentDetails contentDetails;


    public String getKind() {
        return kind;
    }

    public String getEtag() {
        return etag;
    }

    public String getId() {
        return id;
    }

    public Snippet getSnippet() {
        return snippet;
    }

    public ContentDetails getContentDetails() {
        return contentDetails;
    }
}
