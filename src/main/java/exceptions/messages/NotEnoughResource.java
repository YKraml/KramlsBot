package exceptions.messages;

import exceptions.ExceptionMessage;
import waifu.model.dungeon.Inventory;

public class NotEnoughResource implements ExceptionMessage {

    private final Inventory inventory;
    private final long neededMoney;
    private final String resource;

    public NotEnoughResource(Inventory inventory, long neededMoney, String resource) {
        this.inventory = inventory;
        this.neededMoney = neededMoney;
        this.resource = resource;
    }


    @Override
    public String getContent() {
        return ("Du hast nicht genug " + resource + ". Du hast nur %d und brauchst aber %d.").formatted(
            inventory.getMoney(), neededMoney);

    }
}
