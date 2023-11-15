package domain.exceptions.messages;

import domain.waifu.dungeon.Dungeon;
import domain.exceptions.ExceptionMessage;

public class CouldNotPlaceTeamIntoDungeon implements ExceptionMessage {

    private final Dungeon dungeon;
    private final String teamName;


    public CouldNotPlaceTeamIntoDungeon(Dungeon dungeon, String teamName) {
        this.dungeon = dungeon;
        this.teamName = teamName;
    }

    @Override
    public String getContent() {
        return "Konnte Team " + teamName + " nicht in den Dungeon " + dungeon.getName() + " platzieren.";
    }
}
