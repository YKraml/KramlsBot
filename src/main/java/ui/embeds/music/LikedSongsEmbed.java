package ui.embeds.music;

import domain.queue.QueueElement;
import domain.waifu.Player;
import ui.embeds.MyListEmbed;

public class LikedSongsEmbed extends MyListEmbed<QueueElement> {

    public LikedSongsEmbed(Player player, int page) {
        super("Songs von " + player.getName(), player.getLikedSongs(), page, false);
    }
}
