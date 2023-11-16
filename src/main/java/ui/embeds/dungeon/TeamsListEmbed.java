package ui.embeds.dungeon;

import domain.waifu.Player;
import domain.waifu.dungeon.Team;
import ui.embeds.MyListEmbed;

public class TeamsListEmbed extends MyListEmbed<Team> {

    public TeamsListEmbed(Player player, int page) {
        super("Teams von " + player.getName(), player.getTeamList(), page, true);
    }
}
