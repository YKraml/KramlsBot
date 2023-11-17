package ui.reaction;

import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import domain.waifu.Waifu;
import logic.messages.MessageSender;
import logic.waifu.JikanFetcher;
import domain.PlayerLoader;
import domain.WaifuLoader;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import ui.embeds.waifu.WaifuListEmbed;
import ui.messages.messages.WaifuNotFound;
import ui.messages.messages.WaifuStats;
import domain.Emojis;

import java.util.Comparator;

public class WaifuListListener extends MyAbstractListListener<Waifu> {

    private final Player player;
    private final MessageSender messageSender;
    private final PlayerLoader playerLoader;
    private final WaifuLoader waifuLoader;
    private final JikanFetcher jikanFetcher;
    private int sortDirection = -1;


    public WaifuListListener(Player player, MessageSender messageSender, PlayerLoader playerLoader,
                             WaifuLoader waifuLoader, JikanFetcher jikanFetcher) {
        super(player.getWaifuList());
        this.player = player;
        this.messageSender = messageSender;
        this.playerLoader = playerLoader;
        this.waifuLoader = waifuLoader;
        this.jikanFetcher = jikanFetcher;
    }

    @Override
    protected void updateMessage(Message message, int page) {
        message.edit(new WaifuListEmbed(player, page));
    }

    @Override
    protected void reactToCountEmoji(Server server, TextChannel textChannel, User user,
                                     int listPosition)
            throws MyOwnException {
        Waifu waifu = player.getWaifuList().get(listPosition);
        messageSender.send(new WaifuStats(waifu, player, playerLoader, waifuLoader,
                jikanFetcher, messageSender), textChannel);
    }

    @Override
    protected void reactToTooHighCountEmoji(TextChannel textChannel, int listPosition)
            throws MyOwnException {
        messageSender.send(new WaifuNotFound(listPosition), textChannel);
    }

    @Override
    protected void startRoutine(DiscordApi discordApi, Server server, TextChannel textChannel,
                                Message message, User user, Emoji emoji) throws MyOwnException {
        super.startRoutine(discordApi, server, textChannel, message, user, emoji);

        if (emoji.equalsEmoji(Emojis.COOKIE.getEmoji())) {
            sortDirection *= -1;
            player.sortWaifuList((o1, o2) -> (o2.getStatsSum() - o1.getStatsSum()) * sortDirection);
        }

        if (emoji.equalsEmoji(Emojis.STAR2.getEmoji())) {
            sortDirection *= -1;
            player.sortWaifuList((o1, o2) -> (o2.getRarity().getStatsSum() - o1.getRarity().getStatsSum())
                    * sortDirection);
        }

        if (emoji.equalsEmoji(Emojis.ABC.getEmoji())) {
            sortDirection *= -1;
            Comparator<Waifu> comparator = (o1, o2) -> {
                int com = (o2.getAnimeName().compareTo(o1.getAnimeName())) * sortDirection;
                if (com == 0) {
                    com = o2.getName().compareTo(o1.getName());
                }
                return com;
            };
            player.sortWaifuList(comparator);
        }

        updateMessage(message, super.getPageNumber());
    }
}
