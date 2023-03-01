package embeds.dungeon;

import embeds.MyListEmbed;
import waifu.model.dungeon.Dungeon;

import java.util.List;

public class DungeonListEmbed extends MyListEmbed<Dungeon> {


    public DungeonListEmbed(List<Dungeon> dungeonList, int page) {
        super("Dungeons", dungeonList, page, true);
    }
}
