package embeds.waifu;

import de.kraml.Main;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import waifu.model.Player;
import waifu.model.Waifu;

public class WaifuDeletedEmbed extends EmbedBuilder {

    private final Player player;
    private final Waifu deletedWaifu;
    private final int newStardust;
    private final int newCookies;


    public WaifuDeletedEmbed(Player player, Waifu deletedWaifu, int newStardust, int newCookies) {
        this.player = player;
        this.deletedWaifu = deletedWaifu;
        this.newStardust = newStardust;
        this.newCookies = newCookies;

        init();
    }

    private void init() {
      setColor(Main.COLOR);
        setTitle("\"" + deletedWaifu.getName() + "\" verkauft. ");
        setDescription(
            player.getNameTag() + ", du hast " + newStardust + " Stardust und " + newCookies + " Cookies erhalten.");
    }
}
