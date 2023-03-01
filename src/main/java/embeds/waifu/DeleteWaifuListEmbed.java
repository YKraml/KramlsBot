package embeds.waifu;

import embeds.MyListEmbed;
import waifu.model.Player;
import waifu.model.Waifu;

public class DeleteWaifuListEmbed extends MyListEmbed<Waifu> {

    public DeleteWaifuListEmbed(Player player, int page) {
        super("Druecke einen Knopf, um eine Waifu zu loeschen.", player.getWaifuList(), page, false);
    }
}
