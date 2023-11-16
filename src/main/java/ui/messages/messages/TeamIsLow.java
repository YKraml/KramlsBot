package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.waifu.dungeon.Team;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.messages.MyMessageAbs;

public class TeamIsLow extends MyMessageAbs {

    private final Team team;

    public TeamIsLow(Team team) {
        this.team = team;
    }

    @Override
    public void startRoutine(Message message) throws MyOwnException {
        //Ignore.
    }

    @Override
    public EmbedBuilder getContent() throws MyOwnException {
        String pattern = "%s, dein Team \"%s\" hat nur noch %d%% HP.";
        String message = pattern.formatted(team.getPlayer().getNameTag(),
                team.getName(), team.getHpPercentage());
        return this.convertStringToEmbed(message);
    }
}
