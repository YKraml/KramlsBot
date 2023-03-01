package exceptions.messages;

import exceptions.ExceptionMessage;
import waifu.model.Player;
import waifu.model.dungeon.Team;
import waifu.model.Waifu;

public class WaifuInTeam implements ExceptionMessage {

    private final Player player;
    private final Waifu waifu;
    private final Team team;

    public WaifuInTeam(Player player, Team team, Waifu waifu) {
        this.player = player;
        this.waifu = waifu;
        this.team = team;
    }

    @Override
    public String getContent() {
        return player.getName()
                + ", die Waifu \""
                + waifu.getName()
                + "/"
                + waifu.getId()
                + "\" ist schon im Team \""
                + team.getName()
                + "\"";
    }
}
