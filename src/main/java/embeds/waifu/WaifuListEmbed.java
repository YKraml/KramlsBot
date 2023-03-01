package embeds.waifu;

import embeds.MyListEmbed;
import waifu.model.Player;
import waifu.model.Waifu;

public class WaifuListEmbed extends MyListEmbed<Waifu> {

    public WaifuListEmbed(Player player, int page) {
        super("Waifus von " + player.getName(),player.getWaifuList(), page, false);
        this.setThumbnail("");
    }
}
