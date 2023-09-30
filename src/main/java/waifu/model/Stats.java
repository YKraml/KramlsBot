package waifu.model;

public class Stats {

  public static final int MAX_STAR_LEVEL = 10;
  private int baseHp;
  private int baseAtt;
  private int baseDef;
  private int baseInit;
  private Rarities rarity;
  private int starLevel;
  private int xp;

  public Stats(Rarities rarity, int starLevel, int xp, int baseHp, int baseAtt,
      int baseDef, int baseInit) {
    this.rarity = rarity;
    this.starLevel = starLevel;
    this.xp = xp;
    this.baseHp = baseHp;
    this.baseAtt = baseAtt;
    this.baseDef = baseDef;
    this.baseInit = baseInit;
  }

  public Rarities getRarity() {
    return rarity;
  }

  public int getStarLevel() {
    return starLevel;
  }

  public int getBaseHp() {
    return baseHp;
  }

  public int getBaseAtt() {
    return baseAtt;
  }

  public int getBaseDef() {
    return baseDef;
  }

  public int getBaseInit() {
    return baseInit;
  }

  public int getLevel() {
    int level = (int) Math.round(Math.pow(this.xp, 1 / 3d));
    return Math.max(level, 1);
  }

  public int getXp() {
    return xp;
  }

  public int getHp() {
    return calcStat(baseHp);
  }

  public int getAtt() {
    return calcStat(baseAtt);
  }

  public int getDef() {
    return calcStat(baseDef);
  }

  public int getInit() {
    return calcStat(baseInit);
  }

  public int calcStat(int baseStat) {
    return baseStat + ((baseStat * getLevel()) / 25) + baseStat * starLevel;
  }

  public void increaseRarity() {
    this.rarity = rarity.getNextRarity();
    correctNegativeValues();
    correctLowBaseSum();
    correctHighBaseSum();
  }

  public int getStatsSum() {
    return getHp() + getAtt() + getDef() + getInit();
  }

  public int getBaseStatsSum() {
    return this.baseHp + this.baseAtt + this.baseDef + this.baseInit;
  }

  public void addXp(int xp) {
    this.xp += xp;
    if (this.xp > 1000000) {
      this.xp = 1000000;
    }
  }

  private void correctHighBaseSum() {
    int n = 0;
    while (getBaseStatsSum() > this.rarity.getStatsSum()) {
      if (n % 4 == 0 && this.baseHp > 10) {
        this.baseHp--;
      } else if (n % 4 == 1 && this.baseAtt > 10) {
        this.baseAtt--;
      } else if (n % 4 == 2 && this.baseDef > 10) {
        this.baseDef--;
      } else if (n % 4 == 3 && this.baseInit > 10) {
        this.baseInit--;
      }
      n++;
    }

  }

  private void correctLowBaseSum() {
    while (getBaseStatsSum() < this.rarity.getStatsSum()) {
      this.baseHp = (int) (this.baseHp * 1.1);
      this.baseAtt = (int) (this.baseAtt * 1.1);
      this.baseDef = (int) (this.baseDef * 1.1);
      this.baseInit = (int) (this.baseInit * 1.1);
    }
  }

  private void correctNegativeValues() {
    if (this.baseHp < 0) {
      this.baseHp = -this.baseHp;
    }
    if (this.baseAtt < 0) {
      this.baseAtt = -this.baseAtt;
    }
    if (this.baseDef < 0) {
      this.baseDef = -this.baseDef;
    }
    if (this.baseInit < 0) {
      this.baseInit = -this.baseInit;
    }
  }

  public void raiseStarLevelBy(int starLevelToAdd) {
    starLevel += starLevelToAdd;
    if (starLevel > MAX_STAR_LEVEL) {
      starLevel = MAX_STAR_LEVEL;
    }
  }
}