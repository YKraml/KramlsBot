package embeds.dungeon;

import de.kraml.Main;
import java.util.List;
import waifu.model.dungeon.Dungeon;
import waifu.model.dungeon.Record;
import waifu.model.dungeon.Team;
import waifu.model.fighting.Fighter;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class DungeonEmbed extends EmbedBuilder {

  public DungeonEmbed(Dungeon dungeon, List<Team> teams) {

    this.setTitle("Dungeon \"" + dungeon.getName() + "\"");
    this.setColor(Main.COLOR);

    StringBuilder description = new StringBuilder("Rekorde: \n");
    for (Record record : dungeon.getRecords()) {
      description.append(record.print());
      description.append("\n");
    }
    this.setDescription(description.toString());

    int i = 1;
    for (Team team : teams) {
      if (i % 3 == 0) {
        this.addField(".", ".");
      }
      addTeam(team);
      i++;
    }

  }

  private void addTeam(Team team) {
    String title = team.getName();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Ebene: ")
        .append(team.getLevel())
        .append(" | ")
        .append(team.getHpPercentage())
        .append("% HP\n");
    for (Fighter fighter : team.getFighters()) {
      stringBuilder.append(fighter.getWaifu().getName())
          .append(" | lvl ")
          .append(fighter.getWaifu().getLevel())
          .append(" | ")
          .append(fighter.getCurrentHp())
          .append("/")
          .append(fighter.getWaifu().getHp())
          .append(" HP \n");
    }
    String inventoryBody = "%d Euro | %d Stardust | %d Cookies | %d Morphsteine".formatted(
        team.getInventory().getMoney(), team.getInventory().getStardust(),
        team.getInventory().getCookies(), team.getInventory().getMorphStones());

    stringBuilder.append(inventoryBody);
    this.addInlineField(title, stringBuilder.toString());
  }
}
