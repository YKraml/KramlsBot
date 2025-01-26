package domain.waifu.fighting;

public class Attack {

  private final Fighter attacker;
  private final Fighter defender;

  private final int damage;
  private final int healthBeforeAttack;
  private final int healthAfterAttack;


  public Attack(Fighter attacker, Fighter defender, int damage, int healthBeforeAttack) {
    this.attacker = attacker;
    this.defender = defender;
    this.damage = damage;
    this.healthBeforeAttack = healthBeforeAttack;
    this.healthAfterAttack = healthBeforeAttack - damage;
  }

  @Override
  public String toString() {

    if (this.damage == 0) {
      String h = "\"";
      return h + defender.getWaifu().getName() + h + " ist ausgewichen.";
    } else {

      String h = "\"";
      return h + attacker.getWaifu().getName() + h
          + " hat "
          + h + defender.getWaifu().getName() + h
          + " "
          + damage
          + " Schaden verursacht.";
    }

  }

  public Fighter getAttacker() {
    return attacker;
  }

  public Fighter getDefender() {
    return defender;
  }

  public int getDamage() {
    return damage;
  }

  public int getHealthBeforeAttack() {
    return healthBeforeAttack;
  }

  public int getHealthAfterAttack() {
    return healthAfterAttack;
  }
}
