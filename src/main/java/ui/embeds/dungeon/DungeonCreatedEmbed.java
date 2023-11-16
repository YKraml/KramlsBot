package ui.embeds.dungeon;

import domain.waifu.dungeon.Dungeon;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import util.Main;

public class DungeonCreatedEmbed extends EmbedBuilder {

    public DungeonCreatedEmbed(Dungeon dungeon) {

        setColor(Main.COLOR);
        setTitle("Dungeon '%s' wurde erstellt.".formatted(dungeon.getName()));
        setDescription("Ab jetzt können Teams den neuen Dungeon betreten.");
        setThumbnail("https://cdn-icons-png.flaticon.com/512/3898/3898581.png");

        addField("Server Id dieses Servers", dungeon.getServerId());
        addField("Channel Id des Dungeon", dungeon.getChannelId());
        addField("Dungeon Name", dungeon.getName());
        addField("Dungeon Schwierigkeit", String.valueOf(dungeon.getDifficulty()));
    }
}
