package messages.messages;

import exceptions.MyOwnException;
import messages.MyMessage;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import waifu.model.dungeon.Inventory;
import waifu.model.dungeon.Team;

public class TeamKilled extends MyMessage {

    private final Team team;
    private final int level;
    private final Inventory inventory;

    public TeamKilled(Team team, int level, Inventory inventory) {
        this.team = team;
        this.level = level;
        this.inventory = inventory;
    }

    @Override
    protected void startRoutine(Message message) throws MyOwnException {

    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        return this.convertStringToEmbed(team.getPlayer().getNameTag()
                + ", das Team \""
                + team.getName()
                + "\" ist KO gegangen und hat Ebene "
                + level + " erreicht.\n"
                + "Zudem hat es "
                + inventory.getMoney() + " Euro, "
                + inventory.getStardust() + " Stardust und "
                + inventory.getCookies() + " Cookies verloren.");
    }
}
