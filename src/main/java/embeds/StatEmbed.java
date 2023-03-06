package embeds;

import de.kraml.Main;
import waifu.model.Waifu;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import waifu.model.Player;

import java.util.List;

public class StatEmbed extends EmbedBuilder {

  public StatEmbed(Player player, User user, Server server) {

    this.setTitle("Stats von " + player.getName());

    this.setThumbnail(user.getAvatar());

    long time = player.getTimeOnServer(server.getIdAsString());
    String timeString = time / 60 + " Stunden und " + time % 60 + " Minuten";

    long timeSum = player.getTimeSum();
    String timeSumString = timeSum / 60 + " Stunden und " + timeSum % 60 + " Minuten";

    this.addField("Zeit auf Discord", timeSumString);
    this.addField("Zeit auf dem Server", timeString);
    this.addField("Waifus",
        player.getWaifuList().size() + " / " + player.getMaxWaifus() + " Stueck");
    this.addField("Items",
        "Geld: %d | Stardust: %d | Cookies: %d | Morphsteine : %d".formatted(
            player.getInventory().getMoney(),
            player.getInventory().getStardust(), player.getInventory().getCookies(),
            player.getInventory().getMorphStones())

    );

    String guessedString;
    if (player.getRightGuesses() == 1) {
      guessedString = "1 Song";
    } else {
      guessedString = player.getRightGuesses() + " Songs";
    }
    this.addField("Richtig geraten", guessedString);

    this.setColor(Main.COLOR);

    List<Role> roles = user.getRoles(server);
    if (!roles.isEmpty()) {
      StringBuilder roleString = new StringBuilder();
      for (int i = roles.size() - 1; i >= 1; i--) {
        roleString.append(roles.get(i).getName());
        if (i > 1) {
          roleString.append('\n');
        }
      }
      this.addField("Rollen", roleString.toString());
    }

  }

}
