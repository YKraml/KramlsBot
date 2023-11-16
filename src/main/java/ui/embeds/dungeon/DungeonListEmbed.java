package ui.embeds.dungeon;

import domain.waifu.dungeon.Dungeon;
import ui.embeds.MyListEmbed;

import java.util.List;

public class DungeonListEmbed extends MyListEmbed<Dungeon> {


    public DungeonListEmbed(List<Dungeon> dungeonList, int page) {
        super("Dungeons", dungeonList, page, true);
    }
}
