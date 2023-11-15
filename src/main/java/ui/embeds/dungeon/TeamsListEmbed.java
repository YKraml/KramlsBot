package ui.embeds.dungeon;

import ui.embeds.MyListEmbed;
import domain.waifu.Player;
import domain.waifu.dungeon.Team;

public class TeamsListEmbed extends MyListEmbed<Team> {

    public TeamsListEmbed(Player player, int page) {
        super("Teams von " + player.getName(), player.getTeamList(), page, true);
    }
}
