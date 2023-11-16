package domain.waifu;


import java.awt.*;

public enum Rarities {
    COMMON("Common", 0, 100, 100, 1, Color.BLUE, 5),
    RARE("Rare", 25, 1000, 150, 0.50, Color.GREEN, 0),
    EPIC("Epic", 50, 10000, 200, 0.15, Color.MAGENTA, 500),
    LEGENDARY("Legendary", 80, 100000, 300, 0.02, Color.YELLOW, 5000),
    MYTHICAL("Mythical", 100, Integer.MAX_VALUE, 400, 0, Color.WHITE, 5000);

    private final String name;
    private final int minLevel;
    private final int upgradeCost;
    private final int statsSum;
    private final double chance;
    private final Color color;
    private final int sellValue;

    Rarities(String name, int minLevel, int upgradeCost, int statsSum, double chance, Color color,
             int sellValue) {
        this.name = name;
        this.minLevel = minLevel;
        this.upgradeCost = upgradeCost;
        this.statsSum = statsSum;
        this.chance = chance;
        this.color = color;
        this.sellValue = sellValue;
    }

    public static Rarities getRandomRarity() {
        double ranRarity = Math.random();
        if (ranRarity < LEGENDARY.chance) {
            return LEGENDARY;
        } else if (ranRarity < EPIC.chance) {
            return EPIC;
        } else if (ranRarity < RARE.chance) {
            return RARE;
        } else {
            return COMMON;
        }
    }

    public int getMinLevel() {
        return minLevel;
    }

    public int getUpgradeCost() {
        return upgradeCost;
    }

    public Rarities getNextRarity() {
        return switch (this) {
            case COMMON -> RARE;
            case RARE -> EPIC;
            case EPIC -> LEGENDARY;
            case LEGENDARY, MYTHICAL -> MYTHICAL;
        };
    }

    public int getSellValue() {
        return sellValue;
    }

    public int getStatsSum() {
        return statsSum;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public Color getColor() {
        return color;
    }
}
