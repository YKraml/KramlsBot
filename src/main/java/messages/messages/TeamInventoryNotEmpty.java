package messages.messages;

import exceptions.MyOwnException;
import messages.MyMessage;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import waifu.model.dungeon.Team;

public class TeamInventoryNotEmpty extends MyMessage {
    private final Team team;

    public TeamInventoryNotEmpty(Team team) {
        super();
        this.team = team;
    }

    @Override
    protected void startRoutine(Message message) throws MyOwnException {

    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        return this.convertStringToEmbed(team.getPlayer().getNameTag() + ", das Inventar des Teams \"" + team.getName() + "\" ist nicht leer.");
    }
}
