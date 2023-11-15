package ui.embeds.group;

import ui.embeds.MyListEmbed;
import domain.waifu.Group;
import domain.waifu.Player;

public class GroupsListEmbed extends MyListEmbed<Group> {

    public GroupsListEmbed(Player player, int page) {
        super("Gruppen von " + player.getName(),player.getGroupList(), page, false);
        this.setThumbnail("");
    }
}
