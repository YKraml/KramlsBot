package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.waifu.dungeon.Dungeon;
import domain.waifu.dungeon.Team;
import logic.MyMessage;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.embeds.dungeon.DungeonEmbed;

import java.util.List;

public class DungeonMessage implements MyMessage {
    private final List<Team> teams;
    private final Dungeon dungeon;

    public DungeonMessage(Dungeon dungeon, List<Team> teams) {
        this.teams = teams;
        this.dungeon = dungeon;
    }

    @Override
    public void startRoutine(Message message) throws MyOwnException {
        //Do nothing
    }

    @Override
    public EmbedBuilder getContent() throws MyOwnException {
        return new DungeonEmbed(dungeon, teams);
    }
}
