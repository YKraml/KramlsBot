package ui.reaction;

import domain.exceptions.MyOwnException;
import domain.exceptions.messages.FighterNotFound;
import domain.exceptions.messages.TeamIsInDungeon;
import domain.waifu.dungeon.Dungeon;
import domain.waifu.dungeon.Team;
import domain.waifu.fighting.Fighter;
import logic.waifu.DungeonLoader;
import logic.waifu.JikanFetcher;
import logic.waifu.PlayerLoader;
import logic.waifu.WaifuLoader;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.listener.message.reaction.ReactionAddListener;
import ui.embeds.dungeon.TeamEmbed;
import logic.MessageSender;
import ui.messages.messages.ButtonNotForYou;
import ui.messages.messages.DungeonList;
import ui.messages.messages.TeamGavePocketMessage;
import ui.messages.messages.WaifuStats;
import util.Emojis;

import java.util.List;

public class TeamEditListener extends MyAbstractListListener<Fighter> implements
        ReactionAddListener {

    private final Team team;
    private final PlayerLoader playerLoader;
    private final DungeonLoader dungeonLoader;
    private final MessageSender messageSender;
    private final WaifuLoader waifuLoader;
    private final JikanFetcher jikanFetcher;

    public TeamEditListener(Team team, PlayerLoader playerLoader, DungeonLoader dungeonLoader,
                            MessageSender messageSender, WaifuLoader waifuLoader, JikanFetcher jikanFetcher) {
        super(team.getFighters());
        this.team = team;
        this.playerLoader = playerLoader;
        this.dungeonLoader = dungeonLoader;
        this.messageSender = messageSender;
        this.waifuLoader = waifuLoader;
        this.jikanFetcher = jikanFetcher;
    }

    @Override
    protected void updateMessage(Message message, int page) throws MyOwnException {
        message.edit(new TeamEmbed(team));
    }

    @Override
    protected void reactToCountEmoji(Server server, TextChannel textChannel, User user,
                                     int listPosition)
            throws MyOwnException {
        messageSender.send(
                new WaifuStats(team.getFighters().get(listPosition).getWaifu(), team.getPlayer(),
                        playerLoader, waifuLoader, jikanFetcher, messageSender), textChannel);
    }

    @Override
    protected void reactToTooHighCountEmoji(TextChannel textChannel, int listPosition)
            throws MyOwnException {
        throw new MyOwnException(new FighterNotFound(listPosition), null);
    }

    @Override
    protected void startRoutine(DiscordApi discordApi, Server server, TextChannel textChannel,
                                Message message, User user, Emoji emoji) throws MyOwnException {
        super.startRoutine(discordApi, server, textChannel, message, user, emoji);

        if (!user.getIdAsString().equals(team.getPlayer().getId())) {
            messageSender.send(new ButtonNotForYou(user.getMentionTag(), team.getPlayer().getNameTag()),
                    textChannel);
            return;
        }

        if (emoji.equalsEmoji(Emojis.MONEY_BAG.getEmoji())) {
            this.removeMoney(textChannel, message);
        } else if (emoji.equalsEmoji(Emojis.LEFTWARDS_ARROW_WITH_HOOK.getEmoji())) {
            this.returnFromDungeon(textChannel, message);
        } else if (emoji.equalsEmoji(Emojis.CAMPING.getEmoji())) {
            List<Dungeon> dungeonList = dungeonLoader.getAllDungeonsFromServer(server.getIdAsString());
            messageSender.send(new DungeonList(team, dungeonList, playerLoader, messageSender),
                    textChannel);
        } else if (emoji.equalsEmoji(Emojis.ARROWS_COUNTERCLOCKWISE.getEmoji())) {
            message.edit(new TeamEmbed(team));
        } else if (emoji.equalsEmoji(Emojis.HOSPITAL.getEmoji())) {
            team.heal();
            message.edit(new TeamEmbed(team));
        }

        playerLoader.savePlayer(team.getPlayer());
    }

    private void returnFromDungeon(TextChannel textChannel, Message message) {
        textChannel.sendMessage(
                "%s, das Team '%s' hat den Dungeon '%s' verlassen.".formatted(team.getPlayer().getNameTag(),
                        team.getName(), team.getCurrentDungeon().getName()));
        team.leavesDungeon();
        message.edit(new TeamEmbed(team));
    }

    private void removeMoney(TextChannel textChannel, Message message) throws MyOwnException {

        if (team.isInDungeon()) {
            throw new MyOwnException(new TeamIsInDungeon(team), null);
        }

        messageSender.send(new TeamGavePocketMessage(team), textChannel);
        team.givePocketToPlayer();
        message.edit(new TeamEmbed(team));
    }
}
