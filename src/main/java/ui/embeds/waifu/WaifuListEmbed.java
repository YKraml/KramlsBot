package ui.embeds.waifu;

import domain.waifu.Player;
import domain.waifu.Waifu;
import ui.embeds.MyListEmbed;

public class WaifuListEmbed extends MyListEmbed<Waifu> {

    public WaifuListEmbed(Player player, int page) {
        super("Waifus von " + player.getName(), player.getWaifuList(), page, false);
        this.setThumbnail("");
    }
}
