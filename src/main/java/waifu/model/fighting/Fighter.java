package waifu.model.fighting;

import embeds.DisplayableElement;
import waifu.model.Rarities;
import waifu.model.Waifu;

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

    double level = this.waifu.getLevel();
    double basisschaden = 60;
    double att = this.waifu.getAtt();
    double def = defender.getWaifu().getDef();
    double f1 = 1;
    double volltreffer = 1;
    double f2 = 1;
    double z = 100 - (int) (Math.random() * 16);
    double stab = 1;
    double typ1 = 1;
    double typ2 = 1;
    double f3 = 1;

    double c1 = floor(level * 0.4);
    double c2 = floor(c1 + 2);
    double c3 = floor(c2 * basisschaden);
    double c4 = floor(c3 * att);
    double c5 = floor(c4 / (50 * def));
    double c6 = floor(c5 * f1);
    double c7 = floor(c6 + 2);
    double c8 = floor(c7 * volltreffer);
    double c9 = floor(c8 * f2);
    double c10 = floor(c9 * z);
    double c11 = floor(c10 / 100);
    double c12 = floor(c11 * stab);
    double c13 = floor(c12 * typ1);
    double c14 = floor(c13 * typ2);
    double c15 = floor(c14 * f3);

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

    Fighter temp;
    if (fighter1.getWaifu().getInit() < enemy.getWaifu().getInit()) {
      temp = fighter1;
      fighter1 = enemy;
      enemy = temp;
    }

    FightHistory history = new FightHistory(fighter1, enemy);
    while (fighter1.isAlive() && enemy.isAlive()) {

      Attack attack1 = fighter1.attack(enemy);
      history.addAttack(attack1);

      if (enemy.isAlive()) {
        Attack attack2 = enemy.attack(fighter1);
        history.addAttack(attack2);

      }
    }

    if (fighter1.isAlive()) {
      history.setWinner(fighter1);
      fighter1.addXpByKill(enemy.getWaifu().getRarity(), enemy.getWaifu().getLevel());
    } else if (enemy.isAlive()) {
      history.setWinner(enemy);
      enemy.addXpByKill(fighter1.getWaifu().getRarity(), fighter1.getWaifu().getLevel());
    }

    return history;
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
