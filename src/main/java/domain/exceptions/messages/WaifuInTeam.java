package domain.exceptions.messages;

import domain.exceptions.ExceptionMessage;
import domain.waifu.Player;
import domain.waifu.Waifu;
import domain.waifu.dungeon.Team;

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
