package domain.exceptions.messages;

import domain.exceptions.ExceptionMessage;
import domain.waifu.dungeon.Team;

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
