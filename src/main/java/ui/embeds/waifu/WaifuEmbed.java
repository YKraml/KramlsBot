package ui.embeds.waifu;

import domain.waifu.Rarities;
import domain.waifu.Waifu;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class WaifuEmbed extends EmbedBuilder {

    public WaifuEmbed(Waifu waifu) {

        this.setTitle(waifu.getName());
        this.setUrl(waifu.getUrl());
        this.setColor(waifu.getRarity().getColor());
        this.setThumbnail(waifu.getImageUrl());

        Rarities rarity = waifu.getRarity();
        String animeName = waifu.getAnimeName();
        int starLevel = waifu.getStarLevel();
        String starEmoji = waifu.getStarEmoji();
        this.setDescription("%s | %s\n%s".formatted(rarity, animeName,
                starEmoji.repeat(starLevel)));

        double percent = waifu.getXp() / (double) (1000000) * 100;

        String statsTitle = "Stats:";
        String statsBody = "Level: " + waifu.getLevel() + "\n"
                + "Erfahrung: " + String.format("%.2f", percent) + "%" + "\n"
                + "Leben: " + waifu.getHp() + "\n"
                + "Angriff: " + waifu.getAtt() + "\n"
                + "Verteidigung: " + waifu.getDef() + "\n"
                + "Initiative: " + waifu.getInit() + "\n"
                + "Summe: " + waifu.getStatsSum();
        this.addField(statsTitle, statsBody);
    }

}
