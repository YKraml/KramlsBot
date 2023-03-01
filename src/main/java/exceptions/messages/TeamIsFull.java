package exceptions.messages;

import exceptions.ExceptionMessage;
import waifu.model.dungeon.Team;

public class TeamIsFull implements ExceptionMessage {

    private final Team team;


    public TeamIsFull(Team team) {
        this.team = team;
    }

    @Override
    public String getContent() {
        return team.getPlayer().getName() + ", das Team \"" + team.getName() + "\" ist voll.";
    }
}
