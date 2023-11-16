package logic.youtube.model.search;

public class Thumbnails {

    private Default _default;
    private Medium medium;
    private High high;

    public Default getDefault() {
        return _default;
    }

    public void setDefault(Default _default) {
        this._default = _default;
    }

    public Thumbnails withDefault(Default _default) {
        this._default = _default;
        return this;
    }

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    public Thumbnails withMedium(Medium medium) {
        this.medium = medium;
        return this;
    }

    public High getHigh() {
        return high;
    }

    public void setHigh(High high) {
        this.high = high;
    }

    public Thumbnails withHigh(High high) {
        this.high = high;
        return this;
    }

}
