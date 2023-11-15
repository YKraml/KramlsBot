package database.sql.mapper;

import database.sql.entry.AbstractEntrySet;
import database.sql.entry.UserEntrySet;
import domain.waifu.Player;
import domain.waifu.dungeon.Inventory;

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
        inventory.setMorphStones(entry.getMorphStones());

        return new Player(entry.getId(), entry.getName(), entry.getRightGuesses(), entry.getLastDaily(), inventory, entry.getMaxWaifus());
    }


}
