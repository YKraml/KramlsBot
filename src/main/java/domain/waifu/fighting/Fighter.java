package domain.waifu.fighting;

import ui.embeds.DisplayableElement;
import domain.waifu.Rarities;
import domain.waifu.Waifu;

import static java.lang.Math.floor;

public class Fighter implements DisplayableElement {

  private final Waifu waifu;
  private int currentHp;


  public Fighter(Waifu waifu) {
    this.waifu = waifu;
    this.currentHp = waifu.getHp();
  }

  public int getCurrentHp() {
    if (this.currentHp > this.getWaifu().getHp()) {
      this.currentHp = this.getWaifu().getHp();
    }
    return Math.max(currentHp, 0);
  }

  public Waifu getWaifu() {
    return waifu;
  }

  public void setCurrentHp(int currentHp) {
    this.currentHp = Math.min(currentHp, this.waifu.getHp());
  }

  public boolean isAlive() {
    return this.currentHp > 0;
  }

  public Attack attack(Fighter defender) {

    double basisschaden = 60;
    double def = defender.getWaifu().getDef();
    double z = 100 - (int) (Math.random() * 16);

    double c1 = floor((double) this.waifu.getLevel() * 0.4);
    double c2 = floor(c1 + 2);
    double c3 = floor(c2 * basisschaden);
    double c4 = floor(c3 * (double) this.waifu.getAtt());
    double c6 = floor(c4 / (50 * def));
    double c9 = floor(c6 + 2);
    double c10 = floor(c9 * z);
    double c11 = floor(c10 / 100);
    double c15 = floor(c11);

    int damage = (int) (c15 * 1.5);

    //Chance zum Ausweichen
    int speedDiff = defender.getWaifu().getInit() - this.waifu.getInit();
    if (speedDiff > 500) {
      speedDiff = 500;
    }
    if (speedDiff > 0) {
      double r = Math.random();
      if (r <= 0.15 * speedDiff / 100) {
        damage = 0;
      }
    }

    if (damage < 0) {
      damage = 1;
    }

    Attack attack = new Attack(this, defender, damage, defender.getCurrentHp());
    defender.setCurrentHp(defender.getCurrentHp() - damage);

    return attack;
  }

  public void heal() {
    this.currentHp = this.waifu.getHp();
  }

  public FightHistory fight(Fighter enemy) {

    Fighter fighter1 = this;
    Fighter fighter2 = enemy;
    if (fighter1.getWaifu().getInit() < enemy.getWaifu().getInit()) {
      fighter1 = enemy;
      fighter2 = this;
    }

    FightHistory history = startFight(fighter1, fighter2);
    setWinner(fighter1, fighter2, history);

    return history;
  }

  private static FightHistory startFight(Fighter fighter1, Fighter fighter2) {
    FightHistory history = new FightHistory(fighter1, fighter2);
    while (fighter1.isAlive() && fighter2.isAlive()) {

      Attack attack1 = fighter1.attack(fighter2);
      history.addAttack(attack1);

      if (fighter2.isAlive()) {
        Attack attack2 = fighter2.attack(fighter1);
        history.addAttack(attack2);

      }
    }
    return history;
  }

  private static void setWinner(Fighter fighter1, Fighter fighter2, FightHistory history) {
    if (fighter1.isAlive()) {
      history.setWinner(fighter1);
      fighter1.addXpByKill(fighter2.getWaifu().getRarity(), fighter2.getWaifu().getLevel());
    } else if (fighter2.isAlive()) {
      history.setWinner(fighter2);
      fighter2.addXpByKill(fighter1.getWaifu().getRarity(), fighter1.getWaifu().getLevel());
    }
  }


  public void addXpByKill(Rarities killedWaifuRarity, int killedWaifuLevel) {

    int b;
    switch (killedWaifuRarity) {
      case COMMON -> b = 50;
      case RARE -> b = 100;
      case EPIC -> b = 150;
      case LEGENDARY -> b = 200;
      case MYTHICAL -> b = 300;
      default -> b = 10;
    }

    int xp = (b * killedWaifuLevel);
    this.waifu.addXp(xp);

  }

  @Override
  public String getDisplayTitle() {
    return waifu.getDisplayTitle();
  }

  @Override
  public String getDisplayBody() {
    return waifu.getDisplayBody();
  }

  @Override
  public String getDisplayImageUrl() {
    return waifu.getDisplayImageUrl();
  }
}
