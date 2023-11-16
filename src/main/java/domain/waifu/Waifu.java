package domain.waifu;

import domain.DisplayableElement;
import util.Emojis;

public class Waifu implements DisplayableElement {

    private final String id;
    private final Stats stats;
    private final String name;
    private final String animeName;
    private final String idMal;
    private final String url;
    private String imageUrl;


    public Waifu(String id, int idMal, String name, String animeName, String url, String imageUrl,
                 Stats stats) {
        this.id = id;
        this.stats = stats;
        this.imageUrl = imageUrl;
        this.url = url;
        this.idMal = String.valueOf(idMal);
        this.animeName = animeName;
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public Rarities getRarity() {
        return stats.getRarity();
    }

    public int getStarLevel() {
        return stats.getStarLevel();
    }

    public int getBaseHp() {
        return stats.getBaseHp();
    }

    public int getBaseAtt() {
        return stats.getBaseAtt();
    }

    public int getBaseDef() {
        return stats.getBaseDef();
    }

    public int getBaseInit() {
        return stats.getBaseInit();
    }

    public String getName() {
        return name;
    }

    public String getAnimeName() {
        return animeName;
    }

    public String getIdMal() {
        return idMal;
    }

    public String getUrl() {
        return url;
    }

    public int getLevel() {
        return stats.getLevel();
    }

    public int getXp() {
        return stats.getXp();
    }

    public int getHp() {
        return stats.getHp();
    }

    public int getAtt() {
        return stats.getAtt();
    }

    public int getDef() {
        return stats.getDef();
    }

    public int getInit() {
        return stats.getInit();
    }

    public void increaseRarity() {
        stats.increaseRarity();
    }

    public int getStatsSum() {
        return stats.getStatsSum();
    }

    public void addXp(int xp) {
        stats.addXp(xp);
    }

    @Override
    public String getDisplayTitle() {
        return name + " " + getStarEmoji().repeat(stats.getStarLevel());
    }

    @Override
    public String getDisplayBody() {
        return "lvl %1$s | power %2$s | %3$s | %4$s".formatted(stats.getLevel(),
                stats.getStatsSum(), stats.getRarity(), animeName);
    }

    @Override
    public String getDisplayImageUrl() {
        return this.imageUrl;
    }

    public void raiseStarLevelBy(int starLevelToAdd) {
        stats.raiseStarLevelBy(starLevelToAdd);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Waifu waifu = (Waifu) o;

        return id.equals(waifu.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public String getStarEmoji() {
        String starEmoji = Emojis.STAR.getEmoji();
        if (stats.getStarLevel() > 5) {
            starEmoji = Emojis.STAR2.getEmoji();
        }

        if (stats.getStarLevel() == 10) {
            starEmoji = Emojis.SPARKLES.getEmoji();
        }
        return starEmoji;
    }
}
