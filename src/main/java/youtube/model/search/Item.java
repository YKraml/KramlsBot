
package youtube.model.search;

public class Item {

    private String kind;
    private String etag;
    private Id id;
    private Snippet snippet;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Item withKind(String kind) {
        this.kind = kind;
        return this;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public Item withEtag(String etag) {
        this.etag = etag;
        return this;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public Item withId(Id id) {
        this.id = id;
        return this;
    }

    public Snippet getSnippet() {
        return snippet;
    }

    public void setSnippet(Snippet snippet) {
        this.snippet = snippet;
    }

    public Item withSnippet(Snippet snippet) {
        this.snippet = snippet;
        return this;
    }

}
