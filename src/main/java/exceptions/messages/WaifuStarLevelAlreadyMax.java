package exceptions.messages;

import exceptions.ExceptionMessage;
import waifu.model.Waifu;

public class WaifuStarLevelAlreadyMax implements ExceptionMessage {
    private final Waifu waifu;

    public WaifuStarLevelAlreadyMax(Waifu waifu) {
        this.waifu = waifu;
    }

    @Override
    public String getContent() {
        return "Die Waifu " + waifu.getName() + " hat schon das maximale Star level erreicht.";
    }
}
