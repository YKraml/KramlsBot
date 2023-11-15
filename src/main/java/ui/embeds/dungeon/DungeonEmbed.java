package ui.embeds.dungeon;

import util.Main;
import java.util.List;
import domain.waifu.dungeon.Dungeon;
import domain.waifu.dungeon.DungeonRecord;
import domain.waifu.dungeon.Team;
import domain.waifu.fighting.Fighter;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class DungeonEmbed extends EmbedBuilder {

  public DungeonEmbed(Dungeon dungeon, List<Team> teams) {

    this.setTitle("Dungeon \"" + dungeon.getName() + "\"");
    this.setColor(Main.COLOR);

    StringBuilder description = new StringBuilder("Rekorde: \n");
    for (DungeonRecord dungeonRecord : dungeon.getRecords()) {
      description.append(dungeonRecord.print());
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
