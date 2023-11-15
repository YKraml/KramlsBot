package database.sql.commands.user;

import domain.waifu.Player;
import database.sql.commands.SQLCommandCheckExistence;

public class UserExists extends SQLCommandCheckExistence {

    private final Player player;

    public UserExists(Player player) {
        this.player = player;
    }

    @Override
    protected String getCommand() {
        return "select * from " + ("KRAMLSBOT" + "." + "USER") + " where id like " + "'" + player.getId() + "'" + ";";
    }
}
