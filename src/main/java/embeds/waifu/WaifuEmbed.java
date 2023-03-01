package embeds.waifu;

import discord.Emojis;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import waifu.model.Waifu;

public class WaifuEmbed extends EmbedBuilder {

    public WaifuEmbed(Waifu waifu) {

        this.setTitle(waifu.getName());
        this.setUrl(waifu.getUrl());
        this.setColor(waifu.getRarity().getColor());
        this.setThumbnail(waifu.getImageUrl());

        this.setDescription(waifu.getRarity() + " | " + waifu.getAnimeName() + "\n" + String.valueOf(Emojis.STAR.getEmoji()).repeat(Math.max(0, waifu.getStarLevel())));

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
