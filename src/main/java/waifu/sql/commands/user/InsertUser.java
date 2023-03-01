package waifu.sql.commands.user;

import waifu.sql.commands.SQLCommandWithoutResult;
import waifu.model.Player;

public class InsertUser extends SQLCommandWithoutResult {

    private final Player player;

    public InsertUser(Player player) {
        this.player = player;
    }

    @Override
    protected String getCommand() {
        return "insert into KRAMLSBOT.USER values ("
                + "'" + player.getId() + "'"
                + "," + "'" + player.getName() + "'"
                + "," + 0
                + "," + player.getInventory().getMoney()
                + "," + player.getRightGuesses()
                + "," + "'" + player.getLastDaily() + "'"
                + "," + player.getInventory().getStardust()
                + "," + player.getInventory().getCookies()
                + "," + player.getMaxWaifus()
                + ");";
    }
}
