package ui.embeds.dungeon;

import domain.waifu.Waifu;
import domain.waifu.dungeon.Team;
import domain.waifu.fighting.Fighter;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import util.Emojis;
import util.Main;

public class TeamEmbed extends EmbedBuilder {

    public TeamEmbed(Team team) {

        this.setTitle("Team '%s' von '%s'".formatted(team.getName(), team.getPlayer().getName()));
        this.setColor(Main.COLOR);

        this.setDescription(
                "Teamgröße: %d | %s | Schwierigkeit: %d | Tiefe: %d".formatted(team.getTeamSize(),
                        team.getCurrentDungeon().getName(), team.getCurrentDungeon().getDifficulty(),
                        team.getCurrentLevel()));

        if (!team.getFighters().isEmpty()) {
            this.setThumbnail(team.getFighters().get(0).getWaifu().getImageUrl());
        }

        int i = 0;
        for (Fighter fighter : team.getFighters()) {

            Waifu waifu = fighter.getWaifu();

            String title = "%s %s".formatted(Emojis.getCountEmojis()[i], waifu.getName());
            String body = "%d/%d HP\n%s\nLevel %d\n%d Gesamtpower".formatted(fighter.getCurrentHp(),
                    waifu.getHp(), waifu.getRarity(), waifu.getLevel(), waifu.getStatsSum());
            this.addInlineField(title, body);

            i++;
        }

        String title = "Inventar";
        String body = "%d Euro | %d Stardust | %d Cookies | %d Morphsteine ".formatted(
                team.getInventory().getMoney(), team.getInventory().getStardust(),
                team.getInventory().getCookies(), team.getInventory().getMorphStones());
        this.addField(title, body);
    }


}
