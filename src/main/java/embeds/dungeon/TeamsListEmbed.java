package embeds.dungeon;

import embeds.MyListEmbed;
import waifu.model.Player;
import waifu.model.dungeon.Team;

public class TeamsListEmbed extends MyListEmbed<Team> {

    public TeamsListEmbed(Player player, int page) {
        super("Teams von " + player.getName(), player.getTeamList(), page, true);
    }
}
