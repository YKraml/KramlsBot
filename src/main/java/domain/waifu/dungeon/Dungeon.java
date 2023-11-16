package domain.waifu.dungeon;

import ui.embeds.DisplayableElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dungeon implements DisplayableElement {

    private static final Dungeon HOME = new Dungeon("", "", "Home", 0);
    private final String serverId;
    private final String channelId;
    private final String name;
    private final int difficulty;
    private final List<DungeonRecord> dungeonRecords;

    public Dungeon(String serverId, String channelId, String name, int difficulty) {
        this.serverId = serverId;
        this.channelId = channelId;
        this.name = name;
        this.difficulty = difficulty;
        this.dungeonRecords = Collections.synchronizedList(new ArrayList<>());
    }

    public static Dungeon getHOME() {
        return HOME;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getName() {
        return name;
    }

    public List<DungeonRecord> getRecords() {
        return Collections.unmodifiableList(dungeonRecords);
    }

    public String getServerId() {
        return serverId;
    }

    public int getDifficulty() {
        return difficulty;
    }

    @Override
    public String getDisplayTitle() {
        return this.name;
    }

    @Override
    public String getDisplayBody() {
        return "Stufe: " + this.difficulty;
    }

    @Override
    public String getDisplayImageUrl() {
        return null;
    }

    @Override
    public String toString() {
        return "Dungeon{" +
                "serverId='" + serverId + '\'' +
                ", channelId='" + channelId + '\'' +
                ", name='" + name + '\'' +
                ", difficulty=" + difficulty +
                '}';
    }

}
