package ui.embeds.dungeon;

import ui.embeds.MyListEmbed;
import domain.waifu.dungeon.Dungeon;

import java.util.List;

public class DungeonListEmbed extends MyListEmbed<Dungeon> {


    public DungeonListEmbed(List<Dungeon> dungeonList, int page) {
        super("Dungeons", dungeonList, page, true);
    }
}
