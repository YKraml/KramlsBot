package waifu.model;

import java.awt.*;

public enum Rarities {
    COMMON("Common", 0, 100, 100, 0.5, Color.BLUE),
    RARE("Rare", 25, 1000, 150, 0.35, Color.GREEN),
    EPIC("Epic", 50, 10000, 200, 0.13, Color.MAGENTA),
    LEGENDARY("Legendary", 80, 100000, 300, 0.02, Color.YELLOW),
    MYTHICAL("Mythical", 100, Integer.MAX_VALUE, 400, 0, Color.WHITE);

    private final String name;
    private final int minLevel;
    private final int upgradeCost;
    private final int statsSum;
    private final double chance;
    private final Color color;

    Rarities(String name, int minLevel, int upgradeCost, int statsSum, double chance, Color color) {
        this.name = name;
        this.minLevel = minLevel;
        this.upgradeCost = upgradeCost;
        this.statsSum = statsSum;
        this.chance = chance;
        this.color = color;
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
        return switch (this) {
            case COMMON -> 5;
            case RARE -> 50;
            case EPIC -> 500;
            case LEGENDARY, MYTHICAL -> 5000;
        };
    }

    public int getStatsSum() {
        return statsSum;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static Rarities getRandomRarity() {

        double ranRarity = Math.random();
        if (ranRarity < COMMON.chance) {
            return COMMON;
        } else if (ranRarity < COMMON.chance + RARE.chance) {
            return Rarities.RARE;
        } else if (ranRarity < COMMON.chance + RARE.chance + EPIC.chance) {
            return Rarities.EPIC;
        } else if (ranRarity < COMMON.chance + RARE.chance + EPIC.chance + LEGENDARY.chance) {
            return Rarities.LEGENDARY;
        }

        return Rarities.COMMON;
    }

    public Color getColor() {
        return color;
    }
}
