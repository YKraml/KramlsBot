package domain.waifu.dungeon;

import domain.exceptions.MyOwnException;
import domain.exceptions.messages.NotEnoughResource;

public class Inventory {

  private long money;
  private long cookies;
  private long stardust;
  private long morphStones;

  public Inventory(int money, int stardust, int cookies, long morphStones) {
    this.money = money;
    this.stardust = stardust;
    this.cookies = cookies;
    this.morphStones = morphStones;
  }

  public Inventory() {
    this(0,0,0,0);
  }

  public long getMoney() {
    return money;
  }

  public void setMoney(long money) {
    this.money = money;
  }

  public long getCookies() {
    return cookies;
  }

  public void setCookies(long cookies) {
    this.cookies = cookies;
  }

  public long getStardust() {
    return stardust;
  }

  public void setStardust(long stardust) {
    this.stardust = stardust;
  }

  public void addMoney(long money) {
    this.money += money;
  }

  public void addStardust(long stardust) {
    this.stardust += stardust;
  }

  public long getMorphStones() {
    return morphStones;
  }

  public void setMorphStones(long morphStones) {
    this.morphStones = morphStones;
  }

  public void addMorphStones(long morphStone) {
    this.morphStones += morphStone;
  }

  public Inventory losePartialInventory() {

    long remainingMoney = money / (int) (Math.random() * 4 + 2);
    long remainingStardust = stardust / (int) (Math.random() * 4 + 2);
    long remainingCookies = cookies / (int) (Math.random() * 4 + 2);
    long remainingMorphStones = 0;

    long lostMoney = money - remainingMoney;
    long lostStardust = stardust - remainingStardust;
    long lostCookies = cookies - remainingCookies;
    long lostMorphStones = morphStones - remainingMorphStones;

    money = remainingMoney;
    stardust = remainingStardust;
    cookies = remainingCookies;
    morphStones = remainingMorphStones;

    Inventory lostInventory = new Inventory();
    lostInventory.setMoney(lostMoney);
    lostInventory.setStardust(lostStardust);
    lostInventory.setCookies(lostCookies);
    lostInventory.setMorphStones(lostMorphStones);

    return lostInventory;
  }

  public void removeMoney(long amount) throws MyOwnException {
    if (this.money < amount) {
      throw new MyOwnException(new NotEnoughResource(money, amount, "Geld"), null);
    }
    this.money -= amount;
  }

  public void removeStardust(long amount) throws MyOwnException {
    if (this.money < amount) {
      throw new MyOwnException(new NotEnoughResource(stardust, amount, "Stardust"), null);
    }
    this.stardust -= amount;
  }

  public void removeCookies(long amount) throws MyOwnException {
    if (this.money < amount) {
      throw new MyOwnException(new NotEnoughResource(cookies, amount, "Cookies"), null);
    }
    this.cookies -= amount;
  }

  public boolean hasMoney(long amount) {
    return this.money >= amount;
  }

  public void addCookies(long cookies) {
    this.cookies += cookies;
  }

  public void clear() {
    money = 0;
    stardust = 0;
    cookies = 0;
    morphStones = 0;
  }

  public void removeMorphStones(int amount) throws MyOwnException {
    if (morphStones < amount) {
      throw new MyOwnException(new NotEnoughResource(morphStones, amount, "Morphsteine"), null);
    }
    this.morphStones -= amount;
  }
}
