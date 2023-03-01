package waifu.sql.mapper;

import waifu.sql.entry.UserEntrySet;
import waifu.model.Player;
import waifu.model.dungeon.Inventory;
import waifu.sql.entry.AbstractEntrySet;

public class PlayerMapper extends AbstractMapper<Player, UserEntrySet.UserEntry> {


    public PlayerMapper(AbstractEntrySet<UserEntrySet.UserEntry> entrySet) {
        super(entrySet);
    }

    @Override
    Player mapOneEntry(UserEntrySet.UserEntry entry) {

        Inventory inventory = new Inventory();
        inventory.setMoney(entry.getMoney());
        inventory.setStardust(entry.getStardust());
        inventory.setCookies(entry.getCookies());

        return new Player(entry.getId(), entry.getName(), entry.getRightGuesses(), entry.getLastDaily(), inventory, entry.getMaxWaifus());
    }


}
