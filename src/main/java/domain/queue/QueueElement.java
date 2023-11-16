package domain.queue;

import ui.embeds.DisplayableElement;

public class QueueElement implements DisplayableElement, Comparable<QueueElement> {

    private final String name;
    private final String url;
    private final String playerName;
    private final boolean secret;
    private String imageUrl;

    public QueueElement(String name, String url, String playerName) {
        this.name = name;
        this.url = url;
        this.playerName = playerName;
        this.secret = false;

        try {
            String videoId = url.split("\\?v=", 2)[1];
            this.imageUrl = "http://i3.ytimg.com/vi/" + videoId + "/hqdefault.jpg";
        } catch (IndexOutOfBoundsException ignored) {
            this.imageUrl = "";
        }
    }

    public QueueElement(String name, String url, String playerName, boolean secret) {
        this.name = name;
        this.url = url;
        this.playerName = playerName;
        this.secret = secret;

        try {
            String videoId = url.split("\\?v=", 2)[1];
            this.imageUrl = "http://i3.ytimg.com/vi/" + videoId + "/hqdefault.jpg";
        } catch (IndexOutOfBoundsException ignored) {
            this.imageUrl = "";
        }
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isSecret() {
        return secret;
    }

    @Override
    public String getDisplayTitle() {
        if (this.secret) {
            return "[zu erraten]";
        }
        return name + " [" + playerName + "]";
    }

    @Override
    public String getDisplayBody() {
        if (this.secret) {
            return "Secret";
        }
        return this.url;
    }

    @Override
    public String getDisplayImageUrl() {
        if (this.secret) {
            return "";
        }
        return this.imageUrl;
    }

    @Override
    public int compareTo(QueueElement o) {
        return this.url.compareTo(o.getUrl());
    }

}
