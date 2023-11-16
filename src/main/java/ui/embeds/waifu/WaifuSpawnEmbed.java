package ui.embeds.waifu;

import domain.waifu.Waifu;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class WaifuSpawnEmbed extends EmbedBuilder {
    public WaifuSpawnEmbed(Waifu waifu) {

        StringBuilder hint = new StringBuilder();
        char[] array = waifu.getName().toCharArray();
        for (int i = 0; i < array.length; i++) {
            if (i == 0) {
                hint.append(waifu.getName().toCharArray()[0]);
            } else {
                if (array[i] == ' ') {
                    hint.append(" ");
                } else {
                    hint.append(".");
                }
            }
        }

        this.setTitle("Waifu: '%s' ist erschienen!".formatted(hint));
        this.setDescription("Nutze 'claim', um diese Waifu fuer dich zu beanspruchen.");
        this.setColor(waifu.getRarity().getColor());
        this.setImage(waifu.getImageUrl());
    }
}
