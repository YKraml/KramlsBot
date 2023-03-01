package exceptions.messages;

import exceptions.ExceptionMessage;
import waifu.model.dungeon.Dungeon;

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
