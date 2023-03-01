package exceptions.messages;

import exceptions.ExceptionMessage;
import waifu.model.Player;
import waifu.model.Waifu;

public class TeamNotAddedWaifu implements ExceptionMessage {

    private final Player player;
    private final Waifu waifu;

    public TeamNotAddedWaifu(Player player, Waifu waifu) {
        this.player = player;
        this.waifu = waifu;
    }


    @Override
    public String getContent() {
        return player.getNameTag() + ", \"" + waifu.getName() + "\" konnte deinem Team nicht hinzugefuegt werden.";
    }
}
