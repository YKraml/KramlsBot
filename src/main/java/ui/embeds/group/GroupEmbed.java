package ui.embeds.group;

import domain.Emojis;
import domain.waifu.Group;
import domain.waifu.Player;
import domain.waifu.Waifu;
import java.util.Iterator;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import util.Colors;

public class GroupEmbed extends EmbedBuilder {

  public GroupEmbed(Group group, Player player, int page) {

    this.setTitle("Gruppe \"" + group.getName() + "\" von " + player.getName());
    this.setDescription("Seite: " + (page + 1));
    this.setColor(Colors.COLOR);

    Iterator<Waifu> iterator = group.getWaifuList().iterator();

    int i = page * 10;
    while (iterator.hasNext()) {

      if (i < (page + 1) * 10) {
        Waifu waifu = iterator.next();
        String title = Emojis.getCountEmojis()[i % 10] + " " + waifu.getName();
        String body = "ID: " + i
            + " | " + waifu.getRarity()
            + " | " + waifu.getAnimeName();

        this.addField(title, body);
      }

      i++;
    }
  }


}
