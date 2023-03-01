package exceptions.messages;

import exceptions.ExceptionMessage;
import waifu.model.Player;
import waifu.model.dungeon.Team;

public class TeamIsInDungeon implements ExceptionMessage {

  private final Team team;

  public TeamIsInDungeon(Team team) {
    this.team = team;
  }


  @Override
  public String getContent() {
    return "'%s', das Team '%s' ist im Moment in einem Dungeon".formatted(
        team.getPlayer().getName(), team.getName());
  }
}
