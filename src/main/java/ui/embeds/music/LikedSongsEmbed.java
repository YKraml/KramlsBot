package ui.embeds.music;

import ui.embeds.MyListEmbed;
import domain.queue.QueueElement;
import domain.waifu.Player;

public class LikedSongsEmbed extends MyListEmbed<QueueElement> {

    public LikedSongsEmbed(Player player, int page) {
        super("Songs von " + player.getName(), player.getLikedSongs(), page, false);
    }
}
