package domain.waifu.fighting;

import java.util.ArrayList;
import java.util.List;

public class FightHistory {

  private final Fighter fighter1;
  private final Fighter fighter2;
  private final List<Attack> history;
  private Fighter winner;

  public FightHistory(Fighter fighter1, Fighter fighter2) {
    this.fighter1 = fighter1;
    this.fighter2 = fighter2;
    this.history = new ArrayList<>();
  }

  public void addAttack(Attack attack) {
    if (this.history.size() > 250) {
      return;
    }
    this.history.add(attack);
  }

  public Fighter getFighter1() {
    return fighter1;
  }

  public Fighter getFighter2() {
    return fighter2;
  }

  public List<Attack> getHistory() {
    return history;
  }

  public Fighter getWinner() {
    return winner;
  }

  public void setWinner(Fighter winner) {
    this.winner = winner;
  }

  @Override
  public String toString() {

    String h = "\"";

    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder
        .append(h)
        .append(fighter1.getWaifu().getName())
        .append(h)
        .append(" vs ")
        .append(h)
        .append(fighter2.getWaifu().getName())
        .append(h)
        .append("\n");
    for (Attack attack : this.history) {
      stringBuilder.append(attack.toString());
      stringBuilder.append("\n");
    }
    return stringBuilder.toString();
  }
}
