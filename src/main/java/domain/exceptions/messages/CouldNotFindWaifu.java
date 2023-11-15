package domain.exceptions.messages;

import domain.exceptions.ExceptionMessage;

public class CouldNotFindWaifu implements ExceptionMessage {
    private final int waifuId;
    public CouldNotFindWaifu(int waifuId) {
        this.waifuId = waifuId;
    }

    @Override
    public String getContent() {
        return "Konnte die Waifu mit der Nummer " + waifuId + " nicht finden.";
    }
}
