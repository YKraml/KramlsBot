package messages.messages;

import exceptions.MyOwnException;
import messages.MyMessage;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import waifu.model.Player;
import waifu.model.dungeon.Dungeon;
import waifu.model.dungeon.Team;

public class TeamCouldNotEnterDungeonMessage extends MyMessage {
    private final Player player;
    private final Team team;
    private final Dungeon dungeon;

    public TeamCouldNotEnterDungeonMessage(Player player, Team team, Dungeon dungeon) {
        super();
        this.dungeon = dungeon;
        this.player = player;
        this.team = team;
    }

    @Override
    protected void startRoutine(Message message) throws MyOwnException {

    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        return this.convertStringToEmbed(player.getNameTag() + ", das Team \"" + team.getName() + "\" konnte den Dungeon \"" + dungeon.getName() + "\" nicht betreten.");
    }
}
