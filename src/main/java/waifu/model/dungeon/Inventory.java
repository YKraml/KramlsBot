package waifu.model.dungeon;

import exceptions.MyOwnException;
import exceptions.messages.NotEnoughResource;

public class Inventory {

    private long money;
    private long cookies;
    private long stardust;

    public Inventory(int money, int stardust, int cookies) {
        this.money = money;
        this.stardust = stardust;
        this.cookies = cookies;
    }

    public Inventory() {
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

    public Inventory losePartialInventory() {

        long remainingMoney = this.money / (int) (Math.random() * 4 + 2);
        long remainingStardust = this.stardust / (int) (Math.random() * 4 + 2);
        long remainingCookies = this.cookies / (int) (Math.random() * 4 + 2);

        long lostMoney = this.money - remainingMoney;
        long lostStardust = this.stardust - remainingStardust;
        long lostCookies = this.cookies - remainingCookies;

        this.money = remainingMoney;
        this.stardust = remainingStardust;
        this.cookies = remainingCookies;

        Inventory inventory = new Inventory();
        inventory.setMoney(lostMoney);
        inventory.setStardust(lostStardust);
        inventory.setCookies(lostCookies);

        return inventory;
    }

    public void removeMoney(long amount) throws MyOwnException {
        if(this.money < amount){
            throw new MyOwnException(new NotEnoughResource(this, amount, "Geld"), null);
        }
        this.money -= amount;
    }

    public void removeStardust(long amount) throws MyOwnException {
        if(this.money < amount){
            throw new MyOwnException(new NotEnoughResource(this, amount, "Stardust"), null);
        }
        this.stardust -= amount;
    }

    public void removeCookies(long amount) throws MyOwnException {
        if(this.money < amount){
            throw new MyOwnException(new NotEnoughResource(this, amount, "Cookies"), null);
        }
        this.cookies -= amount;
    }

    public boolean hasMoney(long amount) {
        return this.money >= amount;
    }

    public boolean hasStardust(long amount) {
        return this.stardust >= amount;
    }

    public boolean hasCookies(long amount) {
        return this.cookies >= amount;
    }

    public void addCookies(long cookies) {
        this.cookies += cookies;
    }

    public void clear() {
        this.money = 0;
        this.stardust = 0;
        this.cookies = 0;
    }

    public boolean isEmpty() {
        return this.money == 0 && this.stardust == 0 && this.cookies == 0;
    }
}
