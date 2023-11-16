package domain.exceptions.messages;

import domain.exceptions.ExceptionMessage;
import domain.waifu.Waifu;

public class WaifuAlreadyInList implements ExceptionMessage {

    private final Waifu waifu;

    public WaifuAlreadyInList(Waifu waifu) {
        this.waifu = waifu;
    }

    @Override
    public String getContent() {
        return "Die Waifu \"" + waifu.getName() + "\" ist schon in deiner Liste.";
    }
}
