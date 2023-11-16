package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.waifu.dungeon.Dungeon;
import domain.waifu.dungeon.Team;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.messages.MyMessageAbs;

public class TeamEnteredDungeon extends MyMessageAbs {

    private final Team team;
    private final Dungeon dungeon;

    public TeamEnteredDungeon(Team team, Dungeon dungeon) {
        this.dungeon = dungeon;
        this.team = team;
    }

    @Override
    public void startRoutine(Message message) throws MyOwnException {
        //Ignore.
    }

    @Override
    public EmbedBuilder getContent() throws MyOwnException {
        return this.convertStringToEmbed(
                "%s, das Team '%s' hat den Dungeon '%s' betreten.".formatted(team.getPlayer().getNameTag(),
                        team.getName(), dungeon.getName()));
    }

}

