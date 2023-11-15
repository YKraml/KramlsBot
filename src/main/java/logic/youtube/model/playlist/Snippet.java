package logic.youtube.model.playlist;

import java.util.Date;

public class Snippet{
    public Date publishedAt;
    public String channelId;
    public String title;
    public String description;
    public Thumbnails thumbnails;
    public String channelTitle;
    public String playlistId;
    public int position;
    public ResourceId resourceId;
    public String videoOwnerChannelTitle;
    public String videoOwnerChannelId;


    public Date getPublishedAt() {
        return publishedAt;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Thumbnails getThumbnails() {
        return thumbnails;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public int getPosition() {
        return position;
    }

    public ResourceId getResourceId() {
        return resourceId;
    }

    public String getVideoOwnerChannelTitle() {
        return videoOwnerChannelTitle;
    }

    public String getVideoOwnerChannelId() {
        return videoOwnerChannelId;
    }
}
