package ui.embeds.waifu;

import domain.waifu.Player;
import domain.waifu.Waifu;
import ui.embeds.MyListEmbed;

public class DeleteWaifuListEmbed extends MyListEmbed<Waifu> {

    public DeleteWaifuListEmbed(Player player, int page) {
        super("Druecke einen Knopf, um eine Waifu zu loeschen.", player.getWaifuList(), page, false);
    }
}
