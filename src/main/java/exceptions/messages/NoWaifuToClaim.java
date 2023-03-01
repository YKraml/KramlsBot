package exceptions.messages;

import exceptions.ExceptionMessage;

public class NoWaifuToClaim implements ExceptionMessage {
    @Override
    public String getContent() {
        return "Es gibt keine Waifu, deren Namen du erraten könntest.";
    }
}
