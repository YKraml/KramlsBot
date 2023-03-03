package embeds.music;

import embeds.MyListEmbed;
import music.queue.QueueElement;
import waifu.model.Player;

public class LikedSongsEmbed extends MyListEmbed<QueueElement> {

    public LikedSongsEmbed(Player player, int page) {
        super("Songs von " + player.getName(), player.getLikedSongs(), page, false);
    }
}
