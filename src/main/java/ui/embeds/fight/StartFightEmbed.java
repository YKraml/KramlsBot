package ui.embeds.fight;

import domain.waifu.Player;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import util.Colors;

public class StartFightEmbed extends EmbedBuilder {

  public StartFightEmbed(Player player1, Player player2) {

    this.setTitle(player1.getName() + " fordert " + player2.getName() + " zum Kampf heraus.");
    this.setColor(Colors.COLOR);
    this.setDescription("Moechtest du annehmen " + player2.getName() + "?");

  }

}
