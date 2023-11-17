package ui.reaction;

import domain.DisplayableElement;
import domain.exceptions.MyOwnException;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import domain.Emojis;

import java.util.List;

public abstract class MyAbstractListListener<ListType extends DisplayableElement> extends
        MyAbstractReactionListener {

    private final List<ListType> displayableElementList;
    private final int maxPages;
    private int pageNumber;

    public MyAbstractListListener(List<ListType> displayableElementList) {
        this.displayableElementList = displayableElementList;
        this.pageNumber = 0;

        if (displayableElementList.size() % 10 == 0) {
            this.maxPages = displayableElementList.size() / 10;
        } else {
            this.maxPages = displayableElementList.size() / 10 + 1;
        }
    }

    protected void showNextPage(Message message) throws MyOwnException {
        pageNumber++;
        pageNumber = (pageNumber + maxPages) % maxPages;
        updateMessage(message, pageNumber);
    }

    protected void showPreviousPage(Message message) throws MyOwnException {
        pageNumber--;
        pageNumber = (pageNumber + maxPages) % maxPages;
        updateMessage(message, pageNumber);
    }

    protected abstract void updateMessage(Message message, int page) throws MyOwnException;

    protected abstract void reactToCountEmoji(Server server, TextChannel textChannel, User user,
                                              int listPosition) throws MyOwnException;

    protected abstract void reactToTooHighCountEmoji(TextChannel textChannel, int listPosition)
            throws MyOwnException;

    @Override
    protected void startRoutine(DiscordApi discordApi, Server server, TextChannel textChannel,
                                Message message, User user, Emoji emoji) throws MyOwnException {

        if (emoji.equalsEmoji(Emojis.REWIND.getEmoji())) {
            showPreviousPage(message);
        }
        if (emoji.equalsEmoji(Emojis.FAST_FORWARD.getEmoji())) {
            showNextPage(message);
        }

        for (int i = 0; i < 10; i++) {
            if (emoji.equalsEmoji(Emojis.getCountEmojis()[i])) {

                int listPosition = i + 10 * pageNumber;
                if (displayableElementList.size() > listPosition) {
                    this.reactToCountEmoji(server, textChannel, user, i + 10 * pageNumber);
                    updateMessage(message, pageNumber);
                } else {
                    this.reactToTooHighCountEmoji(textChannel, i + 10 * pageNumber);
                }
                return;
            }
        }

    }

    public int getPageNumber() {
        return pageNumber;
    }
}
