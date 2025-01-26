package ui.embeds.dungeon;

import domain.waifu.dungeon.Dungeon;
import java.util.List;
import ui.embeds.MyListEmbed;

public class DungeonListEmbed extends MyListEmbed<Dungeon> {


  public DungeonListEmbed(List<Dungeon> dungeonList, int page) {
    super("Dungeons", dungeonList, page, true);
  }
}
