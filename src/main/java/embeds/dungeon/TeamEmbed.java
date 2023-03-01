package embeds.dungeon;

import de.kraml.Main;
import discord.Emojis;
import waifu.model.dungeon.Team;
import waifu.model.fighting.Fighter;
import waifu.model.Waifu;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class TeamEmbed extends EmbedBuilder {
    public TeamEmbed(Team team) {

        this.setTitle("Team \"" + team.getName() + "\" von " + team.getPlayer().getName());
      this.setColor(Main.COLOR);

        this.setDescription("Teamgroesse: " + team.getTeamSize() + " | " + team.getCurrentDungeon().getDifficulty() + " " + team.getCurrentDungeon().getName());


        if (!team.getFighterList().isEmpty()) {
            this.setThumbnail(team.getFighterList().get(0).getWaifu().getImageUrl());
        }

        int i = 0;
        for (Fighter fighter : team.getFighterList()) {

            Waifu waifu = fighter.getWaifu();

            String title = Emojis.getCountEmojis()[i] + " " + waifu.getName();
            String body = fighter.getCurrentHp() + "/" + waifu.getHp() + " HP"
                    + "\n" + waifu.getRarity()
                    + "\n" + "Level " + waifu.getLevel()
                    + "\n" + waifu.getStatsSum() + " Gesamtpower";
            this.addInlineField(title, body);

            i++;
        }

        String title = "Inventar";
        String body = team.getInventory().getMoney() + " Euro \n"
                + team.getInventory().getStardust() + " Stardust \n"
                + team.getInventory().getCookies() + " Cookies \n";
        this.addField(title, body);
    }


}
