package waifu.model.dungeon;

import embeds.DisplayableElement;
import exceptions.MyOwnException;

import java.util.*;

public class Dungeon implements DisplayableElement {

  private static final Dungeon HOME = new Dungeon("", "", "Home", 0);
  private final String serverId;
  private final String channelId;
  private final String name;
  private final int difficulty;
  private final List<Record> records;

  public Dungeon(String serverId, String channelId, String name, int difficulty) {
    this.serverId = serverId;
    this.channelId = channelId;
    this.name = name;
    this.difficulty = difficulty;
    this.records = Collections.synchronizedList(new ArrayList<>());
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

  public List<Record> getRecords() {
    return Collections.unmodifiableList(records);
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
