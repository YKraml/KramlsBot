package ui.embeds.waifu;

import ui.embeds.MyListEmbed;
import domain.waifu.Player;
import domain.waifu.Waifu;

public class WaifuListEmbed extends MyListEmbed<Waifu> {

    public WaifuListEmbed(Player player, int page) {
        super("Waifus von " + player.getName(),player.getWaifuList(), page, false);
        this.setThumbnail("");
    }
}
