package ui.embeds.dungeon;

import domain.waifu.dungeon.Dungeon;
import java.util.List;
import ui.embeds.MyListEmbed;

public class DungeonsDeletionListEmbed extends MyListEmbed<Dungeon> {

  public DungeonsDeletionListEmbed(List<Dungeon> serverDungeons, int page) {
    super("Dungeon Löschliste", serverDungeons, page, false);
    setDescription("Drücke auf die passende Zahl, um den Dungeon zu löschen.");
    setThumbnail("https://cdn-icons-png.flaticon.com/512/3898/3898581.png");
  }
}
