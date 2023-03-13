package embeds;

import de.kraml.Main;
import java.util.List;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import waifu.model.Player;

public class StatEmbed extends EmbedBuilder {

  public StatEmbed(Player player, User user, Server server) {

    setTitle("Stats von " + player.getName());
    setThumbnail(user.getAvatar());
    setColor(Main.COLOR);
    long time = player.getTimeOnServer(server.getIdAsString());
    long timeSum = player.getTimeSum();
    addField("Zeit auf Discord", "%d Stunden und %d Minuten".formatted(timeSum / 60, timeSum % 60));
    addField("Zeit auf dem Server", "%d Stunden und %d Minuten".formatted(time / 60, time % 60));
    addField("Waifus", player.getWaifuList().size() + " / " + player.getMaxWaifus() + " Stueck");
    addField("Items", "Geld: %d | Stardust: %d | Cookies: %d | Morphsteine : %d".formatted(
        player.getInventory().getMoney(),
        player.getInventory().getStardust(), player.getInventory().getCookies(),
        player.getInventory().getMorphStones())

    );

    addField("Richtig geraten", "%d Songs".formatted(player.getRightGuesses()));
    addRoles(user, server);
  }

  private void addRoles(User user, Server server) {
    List<Role> roles = user.getRoles(server);
    if (!roles.isEmpty()) {
      StringBuilder roleString = new StringBuilder();
      for (int i = roles.size() - 1; i >= 1; i--) {
        roleString.append(roles.get(i).getName());
        if (i > 1) {
          roleString.append('\n');
        }
      }
      addField("Rollen", roleString.toString());
    }
  }

}
