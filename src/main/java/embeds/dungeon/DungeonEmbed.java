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

            String title = team.getName();
            StringBuilder body = new StringBuilder();
            body.append("Ebene: ")
                    .append(team.getLevel())
                    .append(" | ")
                    .append(team.getHpPercentage())
                    .append("% HP\n");
            for (Fighter fighter : team.getFighterList()) {
                body.append(fighter.getWaifu().getName())
                        .append(" | lvl ")
                        .append(fighter.getWaifu().getLevel())
                        .append(" | ")
                        .append(fighter.getCurrentHp())
                        .append("/")
                        .append(fighter.getWaifu().getHp())
                        .append(" HP \n");
            }
            String inventoryBody = team.getInventory().getMoney() + " Euro | "
                    + team.getInventory().getStardust() + " Stardust | "
                    + team.getInventory().getCookies() + " Cookies";

            body.append(inventoryBody);
            this.addInlineField(title, body.toString());


            i++;
        }

    }
}
