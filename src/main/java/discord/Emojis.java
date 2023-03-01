package discord;

import com.vdurmont.emoji.EmojiParser;

public enum Emojis {

    COOKIE(":cookie:"),
    STAR2(":star2:"),
    RING(":ring:"),
    ABC(":abc:"),
    MAGE(":mage:"),
    INFORMATION_SOURCE(":information_source:"),
    MONEY_BAG(":moneybag:"),
    LEFTWARDS_ARROW_WITH_HOOK(":leftwards_arrow_with_hook:"),
    CAMPING(":camping:"),
    X(":x:"),
    CUPCAKE(":cupcake:"),
    MUSICAL_NOTE(":musical_note:"),
    NOTES(":notes:"),
    REWIND(":rewind:"),
    FAST_FORWARD(":fast_forward:"),
    WHITE_CHECK_MARK(":white_check_mark:"),
    ZERO(":zero:"),
    ONE(":one:"),
    TWO(":two:"),
    THREE(":three:"),
    FOUR(":four:"),
    FIVE(":five:"),
    SIX(":six:"),
    SEVEN(":seven:"),
    EIGHT(":eight:"),
    NINE(":nine:"),
    ARROWS_COUNTERCLOCKWISE(":arrows_counterclockwise:"),
    HOSPITAL(":hospital:"),
    STAR(":star:"), HAT(":womans_hat:");

    private final String emoji;

    Emojis(String emojiString) {
        this.emoji = EmojiParser.parseToUnicode(emojiString);
    }

    public String getEmoji() {
        return emoji;
    }

    public static String[] getCountEmojis() {
        return new String[]{
                ZERO.getEmoji(),
                ONE.getEmoji(),
                TWO.getEmoji(),
                THREE.getEmoji(),
                FOUR.getEmoji(),
                FIVE.getEmoji(),
                SIX.getEmoji(),
                SEVEN.getEmoji(),
                EIGHT.getEmoji(),
                NINE.getEmoji()
        };
    }
}
