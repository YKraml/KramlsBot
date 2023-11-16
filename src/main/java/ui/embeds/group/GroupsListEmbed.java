package ui.embeds.group;

import domain.waifu.Group;
import domain.waifu.Player;
import ui.embeds.MyListEmbed;

public class GroupsListEmbed extends MyListEmbed<Group> {

    public GroupsListEmbed(Player player, int page) {
        super("Gruppen von " + player.getName(), player.getGroupList(), page, false);
        this.setThumbnail("");
    }
}
