package embeds.group;

import embeds.MyListEmbed;
import waifu.model.Group;
import waifu.model.Player;

public class GroupsListEmbed extends MyListEmbed<Group> {

    public GroupsListEmbed(Player player, int page) {
        super("Gruppen von " + player.getName(),player.getGroupList(), page, false);
        this.setThumbnail("");
    }
}
