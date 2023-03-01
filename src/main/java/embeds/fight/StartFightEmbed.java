package embeds.fight;

import de.kraml.Main;
import waifu.model.Player;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class StartFightEmbed extends EmbedBuilder {

    public StartFightEmbed(Player player1, Player player2){

        this.setTitle(player1.getName() + " fordert " + player2.getName() + " zum Kampf heraus.");
        this.setColor(Main.COLOR);
        this.setDescription("Moechtest du annehmen " + player2.getName() + "?");

    }

}
